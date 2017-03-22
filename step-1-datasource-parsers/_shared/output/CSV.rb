class CSV

  def self.create(filename)
    filename = java.nio.file.Paths.get(filename).toString()
    filewriter = java.io.OutputStreamWriter.new(java.io.FileOutputStream.new(filename), "UTF-8")

    com.opencsv.CSVWriter.new(filewriter, "\t".to_java(:char), "\"".to_java(:char))
  end
end
