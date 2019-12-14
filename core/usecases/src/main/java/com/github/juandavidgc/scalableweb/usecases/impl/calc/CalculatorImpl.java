package com.github.juandavidgc.scalableweb.usecases.impl.calc;

import com.github.juandavidgc.scalableweb.entities.domain.CalculatorResponse;
import com.github.juandavidgc.scalableweb.entities.domain.Difference;
import com.github.juandavidgc.scalableweb.entities.domain.Parts;
import com.github.juandavidgc.scalableweb.entities.domain.CalculatorResponseStatus;
import com.github.juandavidgc.scalableweb.entities.exceptions.NotEnoughPartsException;
import com.github.juandavidgc.scalableweb.entities.state.StoreManager;
import com.github.juandavidgc.scalableweb.entities.usecases.calc.Calculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jgutierrez on 13/12/2019.
 */
@Component
public class CalculatorImpl implements Calculator {

    @Autowired
    private StoreManager storeManager;

    @Override
    public CalculatorResponse calculate(String id) throws NotEnoughPartsException {
        Parts parts = storeManager.retrievePartsById(id);
        if(noParts(parts) || noEnoughParts(parts)){
            throw new NotEnoughPartsException();
        }

        CalculatorResponse calculatorResponse = new CalculatorResponse(CalculatorResponseStatus.NOT_EQUAL);;
        if(areEqual(parts)){
            calculatorResponse = new CalculatorResponse(CalculatorResponseStatus.EQUAL);
        }
        else if(sameSizeButDifferent(parts)){
            calculatorResponse = new CalculatorResponse(CalculatorResponseStatus.SAME_SIZE_BUT_DIFFERENT);
            calculatorResponse.setDifferenceList(findDifferences(parts));
        }
        return calculatorResponse;
    }

    private List<Difference> findDifferences(Parts parts) {
        List<Difference> differenceList = new ArrayList<>();
        int position = -1;
        int length = 0;
        for(int i = 0; i < parts.getRight().length(); i++){
            if(differentCharAtPosition(parts, i)){
                if(positionNotAlreadySet(position)){
                    position = i;
                }
                length++;
            }
            else {
                if(differenceAlreadyFound(position, length)){
                    differenceList.add(new Difference(position, length));
                    position = -1;
                    length = 0;
                }
            }
        }
        return differenceList;
    }

    private boolean differenceAlreadyFound(int position, int length) {
        return position != -1 && length != 0;
    }

    private boolean differentCharAtPosition(Parts parts, int i) {
        return parts.getRight().charAt(i) != parts.getLeft().charAt(i);
    }

    private boolean positionNotAlreadySet(int position) {
        return position == -1;
    }

    private boolean sameSizeButDifferent(Parts parts) {
        return sameSize(parts) && !areEqual(parts);
    }

    private boolean sameSize(Parts parts) {
        return parts.getLeft().length() == parts.getRight().length();
    }

    private boolean areEqual(Parts parts) {
        return parts.getLeft().equals(parts.getRight());
    }

    private boolean noEnoughParts(Parts parts) {
        return parts.getRight() == null || parts.getLeft() == null;
    }

    private boolean noParts(Parts parts) {
        return parts == null;
    }
}
