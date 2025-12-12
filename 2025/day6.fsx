type Problem = { operation: (int64 -> int64 -> int64); numbers: int64 array }

let toGrid lines =
    lines
    |> Array.map
        (fun line ->
            System.Text.RegularExpressions.Regex.Split(line, "\\s+")
            |> Array.filter (fun value -> value <> "")
        )

let swapColumnsAndRows grid = Array.transpose grid

let numbersFrom row =
    let numberOfNumbers = (Array.length row) - 1

    row
    |> Array.truncate numberOfNumbers
    |> Array.map LanguagePrimitives.ParseInt64

let turnEachRowIntoAProblem rows =
    Array.map
        (fun row ->
            {
                operation =
                    if (Array.last row) = "+" then Operators.(+) else Operators.(*)
                numbers = numbersFrom row
            }
        )
        rows

let toProblems lines =
    lines
    |> toGrid
    |> swapColumnsAndRows
    |> turnEachRowIntoAProblem

let solveThem problems =
    Array.map
        (fun problem ->
            Array.reduce problem.operation problem.numbers
        )
        problems

let findTheGrandTotal solutions =
    Array.reduce Operators.(+) solutions

System.IO.File.ReadAllLines "day6-input"
|> toProblems
|> solveThem
|> findTheGrandTotal
