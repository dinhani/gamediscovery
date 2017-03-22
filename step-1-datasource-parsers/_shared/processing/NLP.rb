require 'stopwords'

module NLP
  include_package 'edu.stanford.nlp.simple'
  include_package 'edu.washington.cs.knowitall.nlp'
  include_package 'edu.washington.cs.knowitall.nlp.extraction'
  include_package 'edu.washington.cs.knowitall.extractor'
  java_import 'org.apache.commons.lang3.reflect.FieldUtils'

  JDocument = Document
  class JDocument
    def clear_cache
      FieldUtils::readStaticField(self.getClass(), "customAnnotators", true).clear()
      FieldUtils::readStaticField(self.getClass(), "annotationPoolKeys", true).clear()
      FieldUtils::readStaticField(self.getClass(), "annotationPool", true).clear()
    end
  end

  class Processor

    def initialize
      @posTags = ["NN", "NNS", "NNP", "NNPS", "JJ"]
      @stopwords_en = Stopwords::Snowball::Filter.new("en")
      @stopwords_pt = Stopwords::Snowball::Filter.new("pt")
    end

    def process(text)
      word_count = parse_word_count(text)
      word_count
    end

    private def prepare_text(text)
    end

    private def parse_word_count(text)
      # create result variables
      all_words = []

      # create doc
      doc = JDocument.new(text)

      # parse each sentence
      doc.sentences.each do |sentence|
        # parse tokens of the sentence
        all_words << sentence.lemmas.zip(sentence.posTags())
          .select { |lemma_and_postag| @posTags.include? lemma_and_postag[1] }
      end

      # if does not have sentences, early return
      if doc.sentences.empty?
        return {}
      end

      # count words
      all_words = all_words.flatten(1)

      # generate unigrams
      unigrams = all_words.map{|lemma_and_postag| lemma_and_postag[0].downcase }
      unigrams = @stopwords_en.filter(unigrams) # remove stopwords
      unigrams = @stopwords_pt.filter(unigrams) # remove stopwords

      # generate bigrams
      bigrams = all_words.each_cons(2)
        .select {|pair_of_lemma_and_postag| (pair_of_lemma_and_postag[0][1] == "NN" and pair_of_lemma_and_postag[1][1] == "NN") or (pair_of_lemma_and_postag[0][1] == "JJ" and pair_of_lemma_and_postag[1][1] == "NN")  }
        .map {|pair_of_lemma_and_postag| pair_of_lemma_and_postag[0][0].downcase + " " + pair_of_lemma_and_postag[1][0].downcase}

      # count words
      all_words = []
      all_words += unigrams
      all_words += bigrams
      word_count = all_words.each_with_object(Hash.new(0)) {|e, h| h[e] += 1} # count words

      # abstract
      summary = doc.sentences[0].toString().gsub("\r", "").gsub("\n", "")

      # free memory
      doc.clear_cache()

      return summary, word_count
    end
  end
end
