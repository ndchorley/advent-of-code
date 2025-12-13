type Problem = { operation: (int64 -> int64 -> int64); numbers: int64 array }

let toGrid lines =
    lines
    |> Array.map
        (fun line ->
            System.Text.RegularExpressions.Regex.Split(line, "\\s+")
            |> Array.filter (fun value -> value <> "")
        )

let swapColumnsAndRows grid = Array.transpose grid

let withoutTheOperation row =
    Array.truncate (Array.length row - 1) row

let asIntegers row =
    Array.map LanguagePrimitives.ParseInt64 row

let numbersFrom row =
    row
    |> withoutTheOperation
    |> asIntegers

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
