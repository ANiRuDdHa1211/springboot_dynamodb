package com.dynamodbtry4.enteties;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

public class OwedToConverter implements DynamoDBTypeConverter<String,OwedTo>
{
    @Override
    public String convert(OwedTo object)
    {
//        OwedTo itemOwedTo = (OwedTo) object;
        String picturesInAString = null;
        try
        {
            if (object != null)
            {
                String str = String.valueOf(object.getAmount());
                picturesInAString = String.format("%sx%sx%s", object.getId(), str );
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return picturesInAString;
    }

    @Override
    public OwedTo unconvert(String s) {

        OwedTo pictures = new OwedTo();
        try
        {
            if (s != null && s.length() != 0)
            {
                String[] data = s.split("x");

                pictures.setId(data[0].trim());
                double d =  Double.parseDouble(data[1].trim());
                pictures.setAmount(d);
            }
        }
        catch (Exception e)
        {
            System.out.println("error while converting class");
            e.printStackTrace();
        }
        return pictures;
    }
}
