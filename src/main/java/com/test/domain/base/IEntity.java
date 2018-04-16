package com.test.domain.base;

import java.io.Serializable;

public interface IEntity {
    Serializable getId();
    boolean isTransient();
}