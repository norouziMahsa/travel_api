package com.afkl.travel.exercise.constants;

public class TravelQuery {

    public static final String GET_BY_LANGUAGE_AND_TYPE_AND_CODE = " SELECT loc " +
            " FROM Location loc LEFT JOIN Location pl on loc.parent = pl.id " +
            " LEFT JOIN Location ppl on pl.parent = ppl.id " +
            " LEFT JOIN Translation t on loc.id = t.location " +
            " WHERE t.language = :language AND loc.type = :type AND" +
            " (ppl.code = :code OR pl.code = :code OR loc.code = :code) ";
}
