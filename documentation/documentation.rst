omniray
=======

1. Objective and Specification
------------------------------
As computer graphics become increasingly more realistic, people have come to expect more and more fidelity and realism from movies and computer games. Looking back a little over thirty years ago, the first Graphical Processing Units were specialized in  drawing lines, arcs, rectangles, filling areas with color and rapidly moving chunks of data inside the memory using a blitter (BLock Image Transfer). While the GPUs of today are still tasked with interpolating and filling triangles, they come with a specialized ISA, a dedicated memory, and thousands of parallel cores. So where is the problem?

Since the very beginning, GPUs have been tasked with filling polygons with pixels. That means everything rendered by a GPU is composed of triangles. This, of course, is a gross simplification of the world around us, and we can only go so far in regards to accuracy. Since the GPU is highly specialized in rendering triangles, we can achieve good performance by rendering textured meshes. This works well for games, because the software needs to repaint the whole screen 30-60 times per second. Unfortunately, the limited capabilities of the GPU have led to a lot of "hacks" or "tricks" that give the illusion of detail where there isn't any. For instance, it is not uncommon to store the normals (orientations) of a surface in a texture, and later use the information in this texture to compute the way the light bounces (or reflects) of a triangle. The contrast between light and shadow that results from this technique gives the illusion of subtle three dimensional details on an otherwise flat surface. Another example is a particle system, where each particle is a transparent texture glued on top of a square (made out of two triangles). A great number of these textures are required to give the impression of smoke, fire, water drops, etc. It sometimes feels like we are abusing the GPU, coming up with clever ways of applying it's limited functionality.

It's clear that the standard mechanism the GPU uses isn't fit for realistic graphics. Our simplified model of the world can be manipulated to offer the illusion of realism, but it will never satisfy us.

2. Functional and Non-functional requirements
---------------------------------------------
Omniray attempts to solve a problem modern artists face today: generating high resolution textures that contain

A. Functional Requirements
==========================

A functional requirement describes what a software system should do, while non-functional requirements place constraints on how the system will do so.
Example: it should accept clients, allow them to place jobs, deliver them the final processed image.

Non-Functional: it should not overload the CPU, it should only post jobs if the overhead is low enough, it should display a progress bar and highlight processed areas, it should not fail if a client fails to finish a job, it should have low (specific) latency.

Accessibility,
Capacity, current and forecast
Compliance
Documentation
Disaster recovery
Efficiency
Effectiveness
Extensibility
Fault tolerance
Interoperability
Maintainability
Privacy
Portability
Quality
Reliability
Resilience
Response time
Robustness
Scalability
Security
Stability
Supportability
Testability

B. Non-Functional Requirements
==============================

- load balancing: the tasks should be distributed evenly among the connected peers, based on their dedicated computing power.
- reliability: each task must be successfully completed. In case a worker fails to finish a task (disconnects, stops responding), the task in question must be reassigned to another worker. A job is not complete until all the tasks are complete. The system must ensure a job finishes while at least one worker is connected.
- efficiency: the tasks and results must be serialized in an efficient way, to minimize network traffic.
- security: there is no security requirement.


3. Theoretical presentation of the involved technologies
--------------------------------------------------------

ZeroMQ
======
ZeroMQ is a networking and concurrency library.
Messages are send and received through an abstraction similar to the old fashioned sockets, but in reality these sockets are only a way of communicating with the zeromq framework. Messages are queued up and managed inside zeromq before they are sent through the network or to the application. This means for instance that clients can start sending messages before the server is started. The messages will be queued up until the server comes online and they can be sent, or until the queue fills and the client blocks, or new messages will be discarded.

Sockets in zeromq have types. The type defines the semantic for the socket: the policy for routing messages inward or outward, queueing, etc. You connect certain types of sockets together (such as a publisher and a subscriber). Sockets work together in "messagin patterns". At the moment of this writing these patterns are hard-coded, but future version of zeromq might allow for user-definable ones. The built-in core patterns are:
	1. Request-reply, which connects a set of clients to a set of services. This is a remote procedure call and task distribution pattern.
	2. Publish-subscribe, which connects a set of publishers to a set of subscribers. This is a data distribution pattern.
	3. Pipeline, which connects nodes in a fan-in/fan-out pattern that can have multiple steps and loops. This is a parallel task distribution and collection pattern.

Looking over the three built-in messaging patterns, it becomes clear that the Pipeline pattern best fits our needs.

The pipeline pattern works by having a Producer send tasks evenly to Consumers which in turn send the results to a Collector. In practice, we achieve this by connecting a PUSH socket on the Producer with PULL sockets on the Consumers. The Consumers in turn each use a PUSH socket that connects to a PULL socket on the Collector.

[IMAGE]

Zeromq works by evenly distributing messages from the PUSH socket to the connected PULL sockets. Each pull socket maintains an internal queue where unprocessed messages are stored. If only one consumer is connected it will receive all the messages and any subsequent Workers that connect after the tasks have been pushed, will be 

Google Protocol Buffers
=======================
Google Protocol Buffers is a serialization format for transforming objects to and from a stream of bytes. It was developed internally by Google and is used in some of their major projects. It's available for a wide range of programming languages, including C++, Python and Java. 

The GPB library works by describing classes through messages. These messages are written in a description language and stored in proto files (.proto). The description serves as a language agnostic way of describing the structure of the object. This way, different languages can painlessly send messages between them. The messages are compiled into language specific classes which have an internal state mirroring the message structure. The GPB objects are immutable, but they work based on a builder pattern. This allows the user to create a builder from an object, set its internal state, and then create a brand new object. The compiled classes also have convenient methods for checking whether or not a field is set, allowing us to build partial objects. The proto messages contain fields that have a type and can be annotated to indicate whether they are optional or repeated. Optional fields are allowed to be missing from an object. Repeated fields indicate an array of the specified type will be used.

4. Architecture
---------------

Project Overview
================

Raytracing
==========

Sampling
========
Computers are discrete devices that display a finite number of pixels, work with a finite number of colors, and in the case of raytracing, sample scenes at a finite number of discrete points. As such, must ray-traced images are subject to 'aliasing', where an alias means a 'substitute'. Here, the images are the substitute of the real world we are trying to render. More specific, pixels are the substitute for the detail contained in the world. The most obvious effect of antialiasing is jagginess, which are the staircase appearance of sharp edges. Other effects include the incorrect rendering of small details, particularly in textures, moire patterns, and color banding if insufficient colors are used. Small objects can also be missed entirely.

The general process used to reduce the ammount of aliasing is called antialiasing. In most cases, antialiasing can't eliminate aliasing, but it can reduce it to acceptable levels, or replace it with noise.

The aliasing techniques employed by the raytracer involve sampling pixels with multiple rays. Although this can be easily implemented by hardwiring a few sampling patterns in the renderer, there are a few reasons for which sampling should be designed and implemented separately. Ray tracing is at it's core, a process of sampling and reconstruction. Not only the scene is sampled. For depth of field, one must use a camera with a finite-area lens and sample the lens. For rendering scenes with area lights and soft shadows, one must sample the light surfaces. For global illumination or glossy reflections and transmission, one must sample BRDFs and BTDFs. For textured surfaces, one must sample the source image. In other words, for the purpose of avoiding clinincally sharp edges, sampling must go beyond simple antialiasing.

Because sampling methods almost always come as a tradeoff between quality and performance, we would like to be able to compare them, use different sampling techniques for different scenes or even different techniques in the same scene. This requires a sampling architecture that allows the raytracer to use sampling points without knowing how they are generated. We discuss such an architecture, as well as the n-rooks, multi-jittered and Hammersley sampling patterns.

Good 2D samplers have a number of characteristics:
1. The samples are evenly distributed over the 2D unit square, so that clumping and gaps are minimized.
2. The 1D projections of the samples in the X and Y direction are also uniformly distributed.
3. No two samples are closer together than some minimum distance.

A. Random Sampling
Random sampling works by generating n^2 samples with random values on X and Y. This is the worst sampling technique, failing all three characteristics.

B. Jittered Sampling
Jittered sampling splits the 2D space into n^2 region, or stratum. It then generates a random sample in every region. The 1D and 2D distributions are better than the ones generated by random sampling, where all the samples could end up in the same region.

C. n-Rooks Sampling
The n-Rooks technique works similar to the jittered sampling, but instead of generating a sample in each region, it chooses n regions, such that if we were to place rooks in each region, they could not overtake one another. Although the 1D projections of the generated samples are uniform, the 2D distribution is almost as bad as random sampling.

D. Multi-Jittered Sampling
Multi-Jittered sampling works by improving the jittered sampling technique and imposing an n-Rooks constraint on the way samples are generated inside a region. [5]

E. Hammersley Sampling
Hammersley sampling was developed in 1960, and is a lot older than jittered, n-Rools and multi-jittered sampling. Hammersley sampling points are not random, because they are based on the representation of numbers on various prime-numbered bases. Hammersley sampling is the only sampling technique that has all the three characterstics necessary for a good sampler. Unfortunately, there is only on Hammersley sequence for a given n.

Samplers will compute the sampling points when they are constructed. This is not only more efficient than computing them every time they are used, it is also required in some cases.

The sampler abstract class will define an interface to be used throught the program and allow the implementing classes to decied how to generate the samples. We choose to make the sampler abstract instead of an interface, because generating the samples is only half the story. In order to prevent samples from repeating in a horizontal or vertical pattern, we have to add additional logic to the way samples are chosen. 

5. Architectural Design (Data, Control, Technologies)
-----------------------------------------------------

6. Implementation
-----------------

7. Deployment
-------------

8. Testing
----------

9. Conclusions, lessons learned and future developments
-------------------------------------------------------

10. User Manual
---------------

11. Bibliography
----------------
[1] ZeroMQ by Pieter Hintjens
[2] Java Concurrency in Practice by Brian Goetz
[3] Computer Networks by Andrew S Tanenbaum (5th eddition)
[4] Ray Tracing from the ground up by Kevin Suffern
[5] Graphics Gems IV by Paul S. Heckbert