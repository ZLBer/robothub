package edu.hust.robothub.core.storage;

import edu.hust.robothub.core.until.Config;

public abstract class AbstractStorage<T> implements Storage<T>,Lifecycle{

    Config config;
    public AbstractStorage(Config config) {
        this.config = config;
    }
}
