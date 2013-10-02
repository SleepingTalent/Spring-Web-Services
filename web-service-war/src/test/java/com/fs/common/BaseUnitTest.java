package com.fs.common;

import org.mockito.MockitoAnnotations;

public abstract class BaseUnitTest {

    protected BaseUnitTest() {
        MockitoAnnotations.initMocks(this);
    }
}
