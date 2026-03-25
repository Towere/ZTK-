package cn.gdsdxy.questionbank.constant;

import java.io.FileWriter;
import java.io.IOException;

public class GenerateCSV {
    public static void main(String[] args) {
        String csvFile = "wrong.csv";

        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("topic\n");

            // Add multiple sets of questions
            addQuestion(writer, "用（ ）表示在单位时间内通过某个网络（或信道、接口）的数据量。 A、 速率         B、 带宽         C、吞吐量        D、 发送速率 正确答案： C");
            addQuestion(writer, "计算机网络最核心的功能是（ ）。 A、 预防病毒         B、 数据通信和资源共享        C、 信息浏览         D、 下载文件 正确答案： B" );

            System.out.println("CSV file generated successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addQuestion(FileWriter writer, String topic) throws IOException {
        String line = String.format("%s\n",
                topic);
        writer.write(line);
    }
}
