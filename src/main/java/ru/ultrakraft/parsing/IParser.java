package ru.ultrakraft.parsing;

import ru.ultrakraft.chamfer.Scan;

import java.util.List;

/**
 * This interface provides for parsing
 * a file to get scanner data
 *
 * @author Malikov E.
 * @since 1.0
 */

public interface IParser {
    List<Scan> getResult();
    void parse(final String file);
}
