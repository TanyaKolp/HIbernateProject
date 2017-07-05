package ru.apache_maven.commands;

import java.util.List;

/**
 * Created by tania on 15.06.17.
 */
public interface Authorization {
    public Result authorize(List<String> input);
}
