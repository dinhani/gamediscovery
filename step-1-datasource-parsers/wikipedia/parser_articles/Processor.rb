require './../../_shared/processing/NLP.rb'
require './../../_shared/processing//Entity.rb'
require './../../_shared/processing//ID.rb'

class Processor

  def initialize()
    @nlp = NLP::Processor.new
  end

  # ============================================================================
  # STATEFUL INDICATOR
  # ============================================================================
  def stateful?
    false
  end

  # ============================================================================
  # PARSING
  # ============================================================================
  def process(content)
    # prepare html
    filename, html = content
    html_doc = org.jsoup.Jsoup.parse(html)

    # parse
    entity = Entity.new
    entity.id = parse_id(html_doc)
    return nil if entity.id.nil?

    entity.id_specific = parse_id_wikipedia(html_doc)
    entity.summary, entity.word_count  = parse_summary_and_word_count(html_doc)

    # lang
    entity.attributes[:lang] = filename[0..1]

    # return
    entity
  end

  private def parse_id(html_doc)
    begin
      title = html_doc.select("h1")[0].text
      title = title.gsub("(video game)", "").gsub("(jogo eletrônico)", "")

      ID::parse(title)
    rescue
      nil
    end
  end

  private def parse_id_wikipedia(html_doc)
    html_doc.select("link[rel=canonical]")[0].attr("href")[30..-1].downcase
  end

  private def parse_summary_and_word_count(html_doc)
    text = html_doc.select("p").to_a.map(&:text).join(" ")
    @nlp.process(text)
  end
end
