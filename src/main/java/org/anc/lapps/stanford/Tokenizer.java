package org.anc.lapps.stanford;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;
import org.lappsgrid.api.Data;
import org.lappsgrid.core.DataFactory;
import org.lappsgrid.discriminator.Types;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Keith Suderman
 */
public class Tokenizer extends AbstractStanfordService
{
   private static final Logger logger = LoggerFactory.getLogger(Tokenizer.class);

   public Tokenizer()
   {
      super("tokenize");
      logger.info("Stanford tokenizer created.");
   }

   @Override
   public long[] requires()
   {
      return new long[]{Types.STANFORD, Types.SENTENCE};
   }

   @Override
   public long[] produces()
   {
      return new long[]{Types.STANFORD, Types.SENTENCE, Types.TOKEN};
   }

   @Override
   public Data execute(Data input)
   {
      logger.info("Executing Stanford tokenizer.");
      Annotation document = new Annotation(input.getPayload());
      service.annotate(document);
      List<String> list = new ArrayList<String>();
      List<CoreLabel> tokens = document.get(CoreAnnotations.TokensAnnotation.class);
      if (tokens == null)
      {
         return DataFactory.error("Stanford tokenizer returned null.");
      }
      for (CoreMap token : tokens)
      {
         list.add(token.toString());
      }
      logger.info("Stanford tokenizer complete.");
      return DataFactory.stringList(list);
   }

}
