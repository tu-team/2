#Design specification scratch


## Thinking life cycle
To be implemented in Haskell.


### Layers 

Architecture is organized in 6 layers

1. instinctive
1. learned
1. deliberative
1. reflective
1. self-reflective
1. self-conscious

But there is *no strict* distinction between layers. Solution is to assign each process analog layer indicator in the range of 0..6. 

1. instinctive = 0..1
1. learned = 1..2
1. deliberative = 2..3
1. reflective = 3..4
1. self-reflective = 4..5
1. self-conscious = 5..6

This way each process could be between the layers or on both layers at the same time. 
Layer indicator should be used for management purposes: processes on higher layer manage(start/stop) processes on lover layers. Extended Pub/Sub could be used for this purposes.