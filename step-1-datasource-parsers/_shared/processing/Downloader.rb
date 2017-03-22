require 'open-uri'

class Downloader

  def download(link, headers = {})
    begin
        puts "Page: #{link}"
        open(link, headers).read
      rescue OpenURI::HTTPError => e
        puts "404: #{link}"
        return nil
      end
  end
end
