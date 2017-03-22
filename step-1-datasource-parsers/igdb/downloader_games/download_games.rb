require './../../_shared/Common.rb'
require './../../_shared/processing/DownloadProcessor.rb'
require './../../_shared/output/ZipSaver.rb'

# =============================================================================
# PARSE INPUT ARGS
# =============================================================================
opt_parser = OptParser.new
options = opt_parser.parse([:output])

# =============================================================================
# EXECUTE PROCESS
# =============================================================================
# create manager classes
executor = Executor.new
executor.threads(20)

executor.log_interval = 10
executor.reader = Reader.new(24391)
executor.saver = ZipSaver.new(options[:output])

processor = DownloadProcessor.new(executor.saver.zipper)
processor.curl 'curl "[URL]" -H "pragma: no-cache" -H "accept-encoding: gzip, deflate, sdch, br" -H "accept-language: pt-BR,pt;q=0.8,en-US;q=0.6,en;q=0.4" -H "upgrade-insecure-requests: 1" -H "user-agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36" -H "accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8" -H "cache-control: no-cache" -H "authority: www.igdb.com" -H "cookie: webp=true; hires=false; webp=true; hires=false; optimizelyEndUserId=oeu1467828531866r0.26098520052212026; _hjIncludedInSample=1; u=renatodinhani; webp=true; hires=false; __cfduid=d329b5ec875a4d7fd1fab8b51479489571474595179; country=76; _ga=GA1.2.734815066.1467828533; _gat=1; optimizelySegments="%"7B"%"225464401136"%"22"%"3A"%"22gc"%"22"%"2C"%"225461761723"%"22"%"3A"%"22search"%"22"%"2C"%"225472642429"%"22"%"3A"%"22false"%"22"%"7D; optimizelyBuckets="%"7B"%"226432321487"%"22"%"3A"%"226446080477"%"22"%"2C"%"226817990031"%"22"%"3A"%"226820890031"%"22"%"2C"%"226925060425"%"22"%"3A"%"226930900091"%"22"%"2C"%"226908600237"%"22"%"3A"%"226925420150"%"22"%"2C"%"227247720227"%"22"%"3A"%"227233980875"%"22"%"2C"%"227463860265"%"22"%"3A"%"227472750053"%"22"%"2C"%"227286290588"%"22"%"3A"%"227251202972"%"22"%"7D; mp_23bc8bdb3ae77125ed43185e744ef3ed_mixpanel="%"7B"%"22distinct_id"%"22"%"3A"%"20"%"22156167b632831d-0d117fbe0d431a-404c0328-1fa400-156167b632962b"%"22"%"2C"%"22"%"24initial_referrer"%"22"%"3A"%"20"%"22"%"24direct"%"22"%"2C"%"22"%"24initial_referring_domain"%"22"%"3A"%"20"%"22"%"24direct"%"22"%"2C"%"22__mps"%"22"%"3A"%"20"%"7B"%"22"%"24os"%"22"%"3A"%"20"%"22Windows"%"22"%"2C"%"22"%"24browser"%"22"%"3A"%"20"%"22Chrome"%"22"%"2C"%"22"%"24browser_version"%"22"%"3A"%"2053"%"2C"%"22"%"24initial_referrer"%"22"%"3A"%"20"%"22"%"24direct"%"22"%"2C"%"22"%"24initial_referring_domain"%"22"%"3A"%"20"%"22"%"24direct"%"22"%"2C"%"22"%"24last_seen"%"22"%"3A"%"20"%"222016-09-23T01"%"3A02"%"3A53"%"22"%"7D"%"2C"%"22__mpso"%"22"%"3A"%"20"%"7B"%"7D"%"2C"%"22__mpa"%"22"%"3A"%"20"%"7B"%"7D"%"2C"%"22__mpu"%"22"%"3A"%"20"%"7B"%"7D"%"2C"%"22__mpap"%"22"%"3A"%"20"%"5B"%"5D"%"2C"%"22"%"24search_engine"%"22"%"3A"%"20"%"22google"%"22"%"7D; csrf=2rOhUnl485vQ3sodcNJdexgkeqQWmJd07PrDbw5x8OCq9j5ZomSr"%"2BJDROwrtRcuFf5a2b1Oqkkr4psf9IQWH4Q"%"3D"%"3D; flash="%"7B"%"7D; _server_session=WERnVlBDVEFRdmVjRzcxaWNCa1Y0QlQ2T3hDdnlVNnM4V3Nsa0lYR1psdktXczJvR0piTi81QUFXRmc5aVJiWHl5RkppaWM4VEFFcEdEYkNWb2swY0lJeTBYWDVTWlNiT2t2Sktia2o3a3RXZUlnNXJySGFDclJQRS9RRkl5anZvaXpqMkpSbUk4OWNTNGtlSUI2elhHTkNHaXFSYW53d1hGNWE4dzZvS3NJQktJOEt0anZCV1RMZGhMM0JTemdzQlJkRkY1bUcyanJBcVMxTDUxcEhwQT09LS13M0RWL3d3Z1k1S2d4dTN5SnpINlV3PT0"%"3D--956bd5101c4beb7ada562540f0b997732d81b69a; mp_mixpanel__c=101; optimizelyPendingLogEvents="%"5B"%"5D" --compressed'

def processor.url(id)
  "https://www.igdb.com/users/list/new?game_id=#{id}"
end
def processor.filename(id)
  "#{id}.html"
end
executor.processor = processor

# execute
executor.execute()
