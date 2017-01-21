package com.ceco.io;

import com.ceco.TestData;
import com.ceco.model.Graph;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 09-Jan-2017
 */
public class InputReaderTest {

    private static final String TEST_RES_DIR = "src/test/resources";

    @Test
    @DisplayName("should read input graph be equal to expected graph - example 1")
    void testReadFromInputGraphExample1() throws IOException {
        Graph<String> expectedGraph = TestData.graphExample1();

        try(InputStream fis = new FileInputStream(new File(TEST_RES_DIR, "input1.txt"))) {
            Graph<String> actualGraph = InputReader.readGraphInputData(fis);
            assertEquals(expectedGraph, actualGraph);
        }
    }

    @Test
    @DisplayName("should read input graph be equal to expected graph - example 2")
    void testReadFromInputGraphExample2() throws IOException {
        Graph<String> expectedGraph = TestData.graphExample2();

        try(InputStream fis = new FileInputStream(new File(TEST_RES_DIR, "input2.txt"))) {
            Graph<String> actualGraph = InputReader.readGraphInputData(fis);
            assertEquals(expectedGraph, actualGraph);
        }
    }

    @Test
    @DisplayName("should read input graph be equal to expected graph - example 3")
    void testReadFromInputGraphExample3() throws IOException {
        Graph<String> expectedGraph = TestData.graphExample3();

        try(InputStream fis = new FileInputStream(new File(TEST_RES_DIR, "input3.txt"))) {
            Graph<String> actualGraph = InputReader.readGraphInputData(fis);
            assertEquals(expectedGraph, actualGraph);
        }
    }

    @Test
    @DisplayName("should read input graph be empty")
    void testReadFromInputEmptyGraph() throws IOException {
        Graph<String> expectedGraph = new Graph<>();

        try(InputStream fis = new FileInputStream(new File(TEST_RES_DIR, "input4.txt"))) {
            Graph<String> actualGraph = InputReader.readGraphInputData(fis);
            assertEquals(expectedGraph, actualGraph);
        }
    }
}
