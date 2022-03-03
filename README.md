# JMTrace

## Compilation Requirements
JDK >= 1.8  
maven 3.6.3 (The program is based on this version of maven to handle dependencies)

## Compilation Instructions

- Reliable internet connection, the program relies on maven to manage java library dependencies.
- In the root directory of the program source code, run the `mvn package` command.
- `./target/jmtrace-1.0-jar-with-dependencies.jar` is the target program.

## Brief Description of Running Tools

The program must be compiled before it can be run.

Run `./jmtrace {args}` in the root directory of the program source code. 

The parameter format is similar to the `java` command.  


### Reference

- ASM 9.2 javadoc https://asm.ow2.io/javadoc/
- TamiFlex https://github.com/secure-software-engineering/tamiflex
- Personal graduation project `DyDy`
- The Java Virtual Machine Instruction Set https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-6.html
- https://stackoverflow.com/questions/57398474/is-there-a-way-to-swap-long-or-double-and-reference-values-on-jvm-stack

