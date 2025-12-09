let findJoltagesStartingWith first restInBank =
    List.fold
        (fun joltages joltage -> 
            let combinedJoltage = LanguagePrimitives.ParseUInt32 $"{first}{joltage}"
            Set.add combinedJoltage joltages
        )
        Set.empty
        restInBank

let rec loop (joltageRatings: Set<uint32>) batteriesRemaining =
    match batteriesRemaining with
    | first :: [] -> joltageRatings.MaximumElement
    | first :: rest ->
        let newJoltageRatings =
            findJoltagesStartingWith first rest

        loop (Set.union newJoltageRatings joltageRatings) rest
    | [] -> 0u

let joltageFor (bank: string) =
    loop
        Set.empty 
        (List.ofArray (bank.ToCharArray()))

let findLargestJoltagePerBank banks =
    Seq.map joltageFor banks

let totalOutputJoltage maximumJoltages =
    Seq.sum maximumJoltages

System.IO.File.ReadLines "day3-input"
|> findLargestJoltagePerBank
|> totalOutputJoltage
