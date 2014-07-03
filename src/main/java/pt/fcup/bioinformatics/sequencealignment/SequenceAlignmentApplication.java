package pt.fcup.bioinformatics.sequencealignment;

import org.apache.commons.configuration.CompositeConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.fcup.bioinformatics.sequencealignment.costmatrix.BlosumCostMatrix;
import pt.fcup.bioinformatics.sequencealignment.costmatrix.MatrixCostType;
import pt.fcup.bioinformatics.sequencealignment.costmatrix.PamCostMatrix;

public class SequenceAlignmentApplication implements IApp
{
    private static Logger logger = LoggerFactory.getLogger(SequenceAlignmentApplication.class);

    private CompositeConfiguration config;

    public SequenceAlignmentApplication(CompositeConfiguration config) {
        this.config = config;
    }

    @Override
    public void start() {

        String[] sequences = config.getStringArray("sequences");
        String sequenceA = sequences[0];
        String sequenceB = sequences[1];

        String matrixCost = config.getString("matrix.cost","NONE").toUpperCase();
        String alignmentType = config.getString("alignment.type","GLOBAL").toUpperCase();

        AlignmentType aType = AlignmentType.valueOf(alignmentType);
        MatrixCostType matrixCostType = MatrixCostType.valueOf(matrixCost);

        AbstractAlignment alignmentMethod;
        AlignmentResult result;

        if(aType.equals(AlignmentType.LOCAL)){
            alignmentMethod = new LocalAlignment();
            result = runAlignment(sequenceA, sequenceB, matrixCostType, alignmentMethod);

        }else {
            alignmentMethod = new GlobalAlignment();
            result = runAlignment(sequenceA, sequenceB, matrixCostType, alignmentMethod);
        }

        logger.info(result.toString());
    }

    private AlignmentResult runAlignment(String sequenceA, String sequenceB, MatrixCostType matrixCostType, AbstractAlignment alignmentMethod) {
        switch (matrixCostType) {
            case BLOSUM: {
                return alignmentMethod.align(new BlosumCostMatrix(), sequenceA, sequenceB);
            }

            case PAM: {
                return alignmentMethod.align(new PamCostMatrix(), sequenceA, sequenceB);
            }

            default: {
                return alignmentMethod.align(sequenceA, sequenceB);
            }
        }
    }

    @Override
    public void shutDown() {
        logger.info("Shutting down ...");
    }
}
