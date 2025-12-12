type IdRange = { first: uint64; last: uint64 }

let parseRange (rangeString: string) =
    let parts = rangeString.Split("-")

    {
        first = LanguagePrimitives.ParseUInt64 parts[0];
        last = LanguagePrimitives.ParseUInt64 parts[1]
    }

let parseIds idStrings =
    idStrings
    |> List.tail
    |> List.map LanguagePrimitives.ParseUInt64

let toIngredientIdRangesAndAvailableIngredientIds lines =
    let listOfLines = List.ofSeq lines

    let blankLine =
        List.findIndex (fun line -> line = "") listOfLines

    let (rangeStrings, idStrings) =
        List.splitAt blankLine listOfLines

    let ranges =
        List.map parseRange rangeStrings

    let ids = parseIds idStrings

    (ranges, ids)

let (freshIdRanges, ids) =
    System.IO.File.ReadLines "day5-input"
    |> toIngredientIdRangesAndAvailableIngredientIds

let isInAny (ranges: IdRange list) id =
    List.exists
        (fun range ->
            id >= range.first && id <= range.last
        )
        ranges

let onlyThoseThatAreFresh freshIdRanges ids =
    List.filter (isInAny freshIdRanges) ids

let countThem ids = List.length ids

ids
|> onlyThoseThatAreFresh freshIdRanges
|> countThem
