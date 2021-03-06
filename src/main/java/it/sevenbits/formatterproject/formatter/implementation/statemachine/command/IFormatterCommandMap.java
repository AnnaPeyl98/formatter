package it.sevenbits.formatterproject.formatter.implementation.statemachine.command;

import it.sevenbits.formatterproject.formatter.implementation.statemachine.state.FormatterState;

/**
 * Interface for classes which will be save map commands
 */
public interface IFormatterCommandMap {
    /**
     * Search next command for this state and name token
     *
     * @param state     current state in formatter
     * @param nameToken name current token
     * @return command for this state and token
     */
    IFormatterCommand getCommand(FormatterState state, String nameToken);
}
