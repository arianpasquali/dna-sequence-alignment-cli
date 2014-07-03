dna-sequence-alignment-cli
==========================

Console tool for DNA or protein sequence alignment.
It uses https://github.com/arianpasquali/dna-sequence-alignment lib.

Supports local and global alignments considering BLOSUM or PAM cost matrix.

Build
-----
Once you clone this repo just run the following command to create the package:

mvn clean package assembly:single

Usage
-----
Untar and run the sample config:

    cd target
    tar zxvf dna-sequence-alignment-cli-bin.tar.gz
    cd dna-sequence-alignment-cli

    java -jar dna-sequence-alignment-cli.jar -input input_example.txt

result:

    ************************************************************
    Version : FCUP Sequence-Alignment-CLI 0.1-SNAPSTHOT
    ************************************************************
    Loading configuration files...
    [configuration] *******************************************
    matrix.cost: PAM
    alignment.type: GLOBAL
    sequences: [GAATTCAGTTA, GGATCGA]
    agent.version: FCUP Sequence-Alignment-CLI 0.1-SNAPSTHOT
    config.file.path: input_example.txt
    ************************************************************
    AlignmentResult{
     method='global:pam'
     score=12

    AATTCAGTTA
    --GA-TCGA-

    }

Config file
------------
    matrix.cost=<PAM,BLOSUM,NONE>
    alignment.type=<GLOBAL,LOCAL>
    sequences=<sequence A>;<sequence B>
