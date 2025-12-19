type State = { beams: int Set; numberOfSplits: int }

let introduceBeam (manifold: string seq) =
    manifold
    |> Seq.head
    |> (fun row ->
            { beams = Set ([row.IndexOf 'S']); numberOfSplits = 0 }
        )

let splitTheBeamAtSplitters state (manifold: string seq) =
    manifold
    |> Seq.fold
        (fun state row ->
            Set.fold
                (fun state beam ->
                    if (row.Chars(beam) = '^') then
                        let beamsWithoutCurrentOne = Set.remove beam state.beams
                        { state with
                            beams = Set.union beamsWithoutCurrentOne (Set [beam - 1; beam + 1])
                            numberOfSplits = state.numberOfSplits + 1
                        }
                    else state
            ) state state.beams
        )
        state

let keepTrackOfTheSplitsAsItMovesThrough (theManifold: string seq) (state: State) =
    theManifold
    |> splitTheBeamAtSplitters state

let tachyonManifold = System.IO.File.ReadLines "day7-input"

tachyonManifold
|> introduceBeam
|> keepTrackOfTheSplitsAsItMovesThrough tachyonManifold
