require 'fileutils'
require './../../_shared/output/Zipper.rb'

java_import 'java.lang.Runtime'
java_import 'java.io.ByteArrayInputStream'
java_import 'java.io.ByteArrayOutputStream'
java_import 'net.coobird.thumbnailator.Thumbnails'
java_import 'net.coobird.thumbnailator.geometry.Positions'
java_import 'org.apache.commons.io.IOUtils'

class Processor

  def initialize(zipper)
    @zipper = zipper
  end

  # ============================================================================
  # STATEFUL
  # ============================================================================
  def stateful?
    false
  end

  # ============================================================================
  # PROCESSING
  # ============================================================================
  def process(content)
    filename, image_bytes = content

    # check if already converted
    filename = generate_filename(filename)
    if @zipper.exist? filename
      return
    end

    # convert
    converted_image_bytes = convert(image_bytes)

    # return
    return filename, converted_image_bytes
  end

  private def generate_filename(filename)
    "#{File.basename filename, '.*'}.png"
  end

  private def convert(image_bytes)
    # prepare
    bais = ByteArrayInputStream.new(image_bytes)
    baos = ByteArrayOutputStream.new

    # convert original
    Thumbnails.of(bais)
      .outputFormat("png")
      .width(200)
      .toOutputStream(baos)

    return baos.toByteArray()
  end
end
