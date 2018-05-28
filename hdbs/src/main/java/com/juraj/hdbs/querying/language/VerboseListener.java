package com.juraj.hdbs.querying.language;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Juraj on 20.3.2018..
 */
public class VerboseListener extends BaseErrorListener {

    private List<String> syntaxErrors = new ArrayList<>();
    private boolean hasErrors = false;

    public VerboseListener(){
        super();
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg,
                            RecognitionException e)
    {
        List<String> stack = ((Parser)recognizer).getRuleInvocationStack();
        Collections.reverse(stack);

        String error = String.format("Rule stack: %s\nLine %d:%d at char %s: %s\n", stack, line, charPositionInLine, offendingSymbol, msg);

        syntaxErrors.add(error);
        hasErrors = true;
    }

    public List<String> getSyntaxErrors(){
        return syntaxErrors;
    }

    public boolean hasErrors(){
        return hasErrors;
    }
}
