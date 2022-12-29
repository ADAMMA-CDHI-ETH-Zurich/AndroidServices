include_directories(${CMAKE_CURRENT_LIST_DIR}/include)

file(GLOB_RECURSE CURRENT_SOURCES
        "${CMAKE_CURRENT_LIST_DIR}/src/*.cpp"
        )

set(CLAID_SOURCES ${CLAID_SOURCES} ${CURRENT_SOURCES})


IF(EXISTS "${CMAKE_CURRENT_LIST_DIR}/java" AND IS_DIRECTORY "${CMAKE_CURRENT_LIST_DIR}/java")
        set(CLAID_JAVA_DIRECTORIES ${CLAID_JAVA_DIRECTORIES} ${CMAKE_CURRENT_LIST_DIR}/java)
endif()