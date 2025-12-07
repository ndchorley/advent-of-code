type Range = { first: uint64; last: uint64}

let rangeFrom (string: string) : Range =
    let parts = string.Split "-"
    
    {
        first = LanguagePrimitives.ParseUInt64 parts[0]
        last = LanguagePrimitives.ParseUInt64 parts[1]
    }

let parseRanges (line: string) =
    line.Split ","
    |> Array.map rangeFrom


let isEven value = value % 2 = 0

let isInvalid (id: string) =
    let halfWay = id.Length / 2

    let firstHalf = id.Substring(0, halfWay)
    let secondHalf = id.Substring(halfWay)

    firstHalf = secondHalf

let findInvalidIds range =
    seq { range.first .. range.last }
    |> Seq.map (fun id -> id.ToString())
    |> Seq.filter (fun id -> isEven id.Length)
    |> Seq.filter isInvalid
    |> Seq.map (fun id -> LanguagePrimitives.ParseUInt64 id)

let invalidIdsInEach ranges =
    Array.map findInvalidIds ranges

let asOneList invalidIdsPerRange =
    Array.fold
        (fun allIds idsForARange -> Seq.append allIds idsForARange)
        Seq.empty
        invalidIdsPerRange

let addThemUp ids =
    Seq.fold
        (fun total id -> total + id)
        0UL
        ids

System.IO.File.ReadAllText "day2-input"
|> parseRanges
|> invalidIdsInEach
|> asOneList
|> addThemUp
|> (fun total -> printfn $"{total}")
