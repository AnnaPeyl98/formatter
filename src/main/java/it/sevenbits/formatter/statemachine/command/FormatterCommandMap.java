package it.sevenbits.formatter.statemachine.command;

import it.sevenbits.Pair;
import it.sevenbits.formatter.statemachine.FormatterContext;
import it.sevenbits.formatter.statemachine.command.implementation.AddNewLineCommand;
import it.sevenbits.formatter.statemachine.command.implementation.AddSpaceCommand;
import it.sevenbits.formatter.statemachine.command.implementation.AddTokenCommand;
import it.sevenbits.formatter.statemachine.command.implementation.MinusIndentCommand;
import it.sevenbits.formatter.statemachine.command.implementation.PlusIndentCommand;
import it.sevenbits.formatter.statemachine.state.FormatterState;

import java.util.HashMap;
import java.util.Map;

/**
 * Class implements IFormatterCommandMap
 */
public class FormatterCommandMap implements IFormatterCommandMap {

    private Map<Pair<FormatterState, String>, IFormatterCommand> repository;

    private static final String MESSAGE = "MESSAGE";
    private static final String SEMICOLON = "SEMICOLON";
    private static final String LEFT_BRACE = "LEFT_BRACE";
    private static final String RIGHT_BRACE = "RIGHT_BRACE";
    private static final String INLINE_COMMNET = "COMMENT_ONE_STRING";
    private static final String MULTILINE_COMMENT = "AFTER_STAR";
    private static final String STRING = "STRING";

    private FormatterCommandMap() {
    }

    /**
     * Constructor create map which save next command for state and lexeme
     *
     * @param context contains token and writer
     */
    public FormatterCommandMap(final FormatterContext context) {
        FormatterState writeState = new FormatterState("WRITE_STATE");
        FormatterState waitState = new FormatterState("WAIT_STATE");
        FormatterState semicolonState = new FormatterState("SEMICOLON_STATE");
        FormatterState leftBraceState = new FormatterState("LEFT_BRACE_STATE");
        FormatterState rightBraceState = new FormatterState("RIGHT_BRACE_STATE");
        FormatterState commentState = new FormatterState("COMMENT_STATE");
        repository = new HashMap<>();

        repository.put(new Pair<>(waitState, null), new AddTokenCommand(context));
        repository.put(new Pair<>(waitState, LEFT_BRACE), new AddTokenCommand(context, new PlusIndentCommand(context)));
        repository.put(new Pair<>(waitState, RIGHT_BRACE), new AddTokenCommand(context));

        repository.put(new Pair<>(writeState, MESSAGE), new AddTokenCommand(context, new AddSpaceCommand(context)));
        repository.put(new Pair<>(writeState, STRING), new AddTokenCommand(context, new AddSpaceCommand(context)));
        repository.put(new Pair<>(writeState, SEMICOLON), new AddTokenCommand(context));
        repository.put(new Pair<>(writeState, LEFT_BRACE), new AddTokenCommand(context,
                new AddSpaceCommand(context, new PlusIndentCommand(context))));
        repository.put(new Pair<>(writeState, RIGHT_BRACE), new AddTokenCommand(context,
                new AddNewLineCommand(context, new MinusIndentCommand(context))));
        repository.put(new Pair<>(writeState, null), new AddTokenCommand(context, new AddNewLineCommand(context)));

        repository.put(new Pair<>(semicolonState, MESSAGE), new AddTokenCommand(context, new AddNewLineCommand(context)));
        repository.put(new Pair<>(semicolonState, STRING), new AddTokenCommand(context, new AddNewLineCommand(context)));
        repository.put(new Pair<>(semicolonState, SEMICOLON), new AddTokenCommand(context));
        repository.put(new Pair<>(semicolonState, LEFT_BRACE), new AddTokenCommand(context,
                new PlusIndentCommand(context, new AddNewLineCommand(context))));
        repository.put(new Pair<>(semicolonState, RIGHT_BRACE), new AddTokenCommand(context,
                new AddNewLineCommand(context, new MinusIndentCommand(context))));
        repository.put(new Pair<>(semicolonState, null), new AddTokenCommand(context, new AddNewLineCommand(context)));

        repository.put(new Pair<>(leftBraceState, MESSAGE), new AddTokenCommand(context, new AddNewLineCommand(context)));
        repository.put(new Pair<>(leftBraceState, STRING), new AddTokenCommand(context, new AddNewLineCommand(context)));
        repository.put(new Pair<>(leftBraceState, SEMICOLON), new AddTokenCommand(context, new AddNewLineCommand(context)));
        repository.put(new Pair<>(leftBraceState, LEFT_BRACE), new AddTokenCommand(context,
                new PlusIndentCommand(context, new AddNewLineCommand(context))));
        repository.put(new Pair<>(leftBraceState, RIGHT_BRACE), new AddTokenCommand(context,
                new AddNewLineCommand(context, new MinusIndentCommand(context))));
        repository.put(new Pair<>(leftBraceState, null), new AddTokenCommand(context, new AddNewLineCommand(context)));

        repository.put(new Pair<>(rightBraceState, MESSAGE), new AddTokenCommand(context, new AddNewLineCommand(context)));
        repository.put(new Pair<>(rightBraceState, STRING), new AddTokenCommand(context, new AddNewLineCommand(context)));
        repository.put(new Pair<>(rightBraceState, SEMICOLON), new AddTokenCommand(context, new AddNewLineCommand(context)));
        repository.put(new Pair<>(rightBraceState, LEFT_BRACE), new AddTokenCommand(context,
                new PlusIndentCommand(context, new AddNewLineCommand(context))));
        repository.put(new Pair<>(rightBraceState, RIGHT_BRACE), new AddTokenCommand(context,
                new AddNewLineCommand(context, new MinusIndentCommand(context))));
        repository.put(new Pair<>(rightBraceState, null), new AddTokenCommand(context, new AddNewLineCommand(context)));

        repository.put(new Pair<>(commentState, MESSAGE), new AddTokenCommand(context, new AddNewLineCommand(context)));
        repository.put(new Pair<>(commentState, STRING), new AddTokenCommand(context, new AddNewLineCommand(context)));
        repository.put(new Pair<>(commentState, SEMICOLON), new AddTokenCommand(context, new AddNewLineCommand(context)));
        repository.put(new Pair<>(commentState, LEFT_BRACE), new AddTokenCommand(context,
                new PlusIndentCommand(context, new AddNewLineCommand(context))));
        repository.put(new Pair<>(commentState, RIGHT_BRACE), new AddTokenCommand(context,
                new AddNewLineCommand(context, new MinusIndentCommand(context))));
        repository.put(new Pair<>(commentState, null), new AddTokenCommand(context, new AddNewLineCommand(context)));
    }

    /**
     * Search next command for this state and name token
     *
     * @param state     current state in formatter
     * @param nameToken name current token
     * @return command for this state and token
     */
    @Override
    public IFormatterCommand getCommand(final FormatterState state, final String nameToken) {
        IFormatterCommand command = repository.get(new Pair<>(state, nameToken));
        if (command == null) {
            return repository.get(new Pair<>(state, null));
        }
        return command;
    }
}
