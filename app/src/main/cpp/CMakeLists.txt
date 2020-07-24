
cmake_minimum_required(VERSION 3.15)

project(SageGraphics
        VERSION 0.0.1)


set(JNI_WRAPPER OFF CACHE BOOL
        Whether to include the JNI Wrapper code so that the
         Sage Graphics library can be called from the JVM.)


add_subdirectory(src)