require_relative './Downloader.rb'

class DownloadProcessor

  def initialize(zipper)
    @curl = nil

    @downloader = Downloader.new
    @headers = {}

    @zipper = zipper
  end

  def stateful?
    false
  end

  def curl(curl)
    @curl = curl
  end

  def headers(headers)
    @headers = headers
  end

  def process(id)
    # do not download if already processed
    generated_filename = filename(id)
    if @zipper.exist? generated_filename
        return nil
    end

    # download
    url_to_download = url(id)
    if url_to_download.nil?
      return nil
    end

    if not @curl.nil?
      # use curl
      curl_to_execute = @curl.gsub("[URL]", url_to_download)
      downladed_content = `#{curl_to_execute}`
    else
      # use downloader
      downladed_content = @downloader.download(url_to_download, @headers)
    end

    # return filename and content
    return generated_filename, downladed_content
  end
end
