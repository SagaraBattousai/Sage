
set(SRC_FILES Triangle.cpp Colour.cpp)

add_library(SAGE_GRAPHICS SHARED ${SRC_FILES})

target_include_directories(SAGE_GRAPHICS PUBLIC ${CMAKE_SOURCE_DIR}/include)