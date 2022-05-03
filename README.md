## First, the disclaimer

Since I'm migrating from embedded software development to Back-end, I'm starting a series of projects, this one here being the very first one, to learn and practice. 

I have no compromise to keep it fully functional or to make something quasi state-of-the-art, but I do want to make everything **"right"**, i.e., with good practices. 

If anything looks "wrong" it might be because I dunno (yet) how to make it properly or because this project will be a Work In Progress forever.

# FrameworklessApi

The intent of this project is to create a Rest API without the help of any framework. Yes,no Spring, no Javalin or any other framework. Just the good and old java and JDBC.
Mind you, it is just for study, exploration and adventure.


## The proposed project

The FrameworklessApi gives access to a small and very simple service dedicated to record, manipulate and expose data about my wife's Root Canal patients. Since some data
that might be added is sensitive, only mock data will be available in this Repository. 

### The Model

The service will have data representation of:

* Patients
* Root Canal instruments, like:
  * File
  * Reamers
* Most common symptoms
* Treated tooth

In the future, a proper schema will be added on this documentation.


