package com.github.juandavidgc.scalableweb.business;

import com.github.juandavidgc.scalableweb.entities.CalculatorResponse;
import com.github.juandavidgc.scalableweb.entities.Difference;
import com.github.juandavidgc.scalableweb.entities.Parts;
import com.github.juandavidgc.scalableweb.exceptions.NotEnoughPartsException;
import com.github.juandavidgc.scalableweb.state.StoreManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.github.juandavidgc.scalableweb.entities.CalculatorResponseStatus.*;

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

        CalculatorResponse calculatorResponse = new CalculatorResponse(NOT_EQUAL);;
        if(areEqual(parts)){
            calculatorResponse = new CalculatorResponse(EQUAL);
        }
        else if(sameSizeButDifferent(parts)){
            calculatorResponse = new CalculatorResponse(SAME_SIZE_BUT_DIFFERENT);
            calculatorResponse.setDifferenceList(calculateOffsets(parts));
        }
        return calculatorResponse;
    }

    private List<Difference> calculateOffsets(Parts parts) {
        List<Difference> differenceList = new ArrayList<>();
        int position = -1;
        int length = 0;
        for(int i = 0; i < parts.getRight().length(); i++){
            if(parts.getRight().charAt(i) != parts.getLeft().charAt(i)){
                if(position == -1){
                    position = i;
                }
                length++;
            }
            else {
                if(position != -1 && length != 0){
                    differenceList.add(new Difference(position, length));
                    position = -1;
                    length = 0;
                }
            }
        }
        return differenceList;
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
