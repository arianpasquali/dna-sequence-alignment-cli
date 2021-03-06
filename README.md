dna-sequence-alignment-cli
==========================

Console tool for DNA or protein sequence alignment.
Console interface for the dna-sequence-alignment library.
Supports local and global alignments considering BLOSUM or PAM cost matrix.

NOTE: see also the web version [dna-sequence-alignment-web](https://github.com/arianpasquali/dna-sequence-alignment-web)


Dependency
----------
It uses https://github.com/arianpasquali/dna-sequence-alignment lib.
Clone it and install it on your local maven repo:

    mvn install:install-file -Dfile=dna-sequence-alignment-0.1-SNAPSHOT.jar \
                             -DgroupId=pt.fcup.bioinformatics \
                             -DartifactId=dna-sequence-alignment \
                             -Dversion=0.1-SNAPSHOT \
                             -Dpackaging=jar

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

    java -jar dna-sequence-alignment-cli.jar -input pyrococcus_vs_thermococcus.txt

This 'pyrococcus_vs_thermococcus.txt' example is an input file for global alignment considering [Pyrococcus furiosus](http://www.ncbi.nlm.nih.gov/protein/499322557) against [Thermococcus barophilus](http://www.ncbi.nlm.nih.gov/protein/503232942)

result:

    ************************************************************
    Version : FCUP Sequence-Alignment-CLI 0.1-SNAPSTHOT
    ************************************************************
    Loading configuration files...
    [configuration] *******************************************
    matrix.cost: BLOSUM
    alignment.type: GLOBAL
    sequences: [MAWKVSVDQDTCIGDAICASLCPDVFEMNDEGKAQPKVEVIEDEELYNCAKEAMEACPVS, MKVKLDKDTCIGCGVCASICPDVFEMDDDGKAKVIMEETDLECAKEAAESCPTGSI]
    agent.version: FCUP Sequence-Alignment-CLI 0.1-SNAPSTHOT
    config.file.path: ../../pyrococcus_vs_thermococcus.txt
    ************************************************************
    AlignmentResult{
     method='global:blosum'
     score=12

    AWKVSVDQDTCIGDAICASLCPDVFEMNDEGKAQPKVEVIEDEELYNCAKEA-MEACPVS
    --KVKLDKDTCIGCGVCASICPDVFEMDDDGKA-KVIMEETDLECAKEA--AESCPTGSI

    }

Config file
------------
    matrix.cost=<PAM,BLOSUM,NONE>
    alignment.type=<GLOBAL,LOCAL>
    sequences=<sequence A>;<sequence B>
