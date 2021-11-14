package dev.tonholo.chronosimplesapi.validator;

public interface Validation<T> {
    void validate(T t);
}