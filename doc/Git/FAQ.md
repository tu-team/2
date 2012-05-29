# General FAQ for Git

 1. Corrupted header error: error: RPC failed; result=22, HTTP code = 0 fatal: write error: Broken pipe 
   2. Execute this one in git directory _git config --global http.postBuffer 524288000_