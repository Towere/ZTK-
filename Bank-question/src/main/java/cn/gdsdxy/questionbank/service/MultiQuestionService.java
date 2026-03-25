package cn.gdsdxy.questionbank.service;

import cn.gdsdxy.questionbank.dto.manage.SearchPageQuestionDto;
import cn.gdsdxy.questionbank.dto.question.FillQuestionDto;
import cn.gdsdxy.questionbank.dto.question.MultiQuestionDto;
import cn.gdsdxy.questionbank.entity.questions.FillQuestion;
import cn.gdsdxy.questionbank.entity.questions.JudgeQuestion;
import cn.gdsdxy.questionbank.entity.questions.MultiQuestion;
import cn.gdsdxy.questionbank.entity.questions.Question;
import cn.gdsdxy.questionbank.mapper.MultiQuestionMapper;
import cn.gdsdxy.questionbank.repository.FillQuestionRepository;
import cn.gdsdxy.questionbank.repository.MultiQuestionRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class MultiQuestionService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    MultiQuestionRepository multiQuestionRepository;
    @Autowired
    private MultiQuestionMapper multiQuestionMapper;

    public List<MultiQuestionDto> getMultiQuestionByPage(){
        List<MultiQuestion> multiQuestionList =multiQuestionRepository.selectList(null);
        System.out.println(multiQuestionList);
        List<MultiQuestionDto> multiQuestionDtoList = multiQuestionList.stream()//把列表变成流
                .map(x->new MultiQuestionDto(x.getQuestion(),x.getRightAnswer(),x.getScore()))
                .collect(Collectors.toList());//把流变成列表
        System.out.println(multiQuestionDtoList);
        return multiQuestionDtoList;
    }
    public List<MultiQuestion> getAllMultiQuestion(){
        QueryWrapper<MultiQuestion> multiQuestionQueryWrapper = new QueryWrapper<>();
        List<MultiQuestion> multiQuestionList =multiQuestionRepository.selectList(multiQuestionQueryWrapper);
        return multiQuestionList;
    }

    public List<MultiQuestion> generateRandomExam(int numberOfQuestions) {
        QueryWrapper<MultiQuestion> multiQuestionQueryWrapper = new QueryWrapper<>();
        List<MultiQuestion> questions =multiQuestionRepository.selectList(multiQuestionQueryWrapper);
        List<MultiQuestion> randomQuestions = new ArrayList<>();

        // 随机选择题目
        Random random = new Random();
        for (int i = 0; i < numberOfQuestions; i++) {
            int randomIndex = random.nextInt(questions.size());
            randomQuestions.add(questions.get(randomIndex));
            questions.remove(randomIndex);
        }
        return randomQuestions;
    }
    public void addMultiQuestion(MultiQuestion multiQuestion) {
        multiQuestionRepository.insert(multiQuestion);
    }
    public List<MultiQuestion> findByIdAndType(Integer PaperId) {
        return multiQuestionMapper.findByIdAndType(PaperId);
    }
    public List<MultiQuestion> findByQuestion(SearchPageQuestionDto searchPageQuestionDto) {
        return multiQuestionMapper.findByQuestion(searchPageQuestionDto);
    }
    public MultiQuestion findOnlyQuestionId() {
        return multiQuestionMapper.findOnlyQuestionId();
    }

    @Transactional
    public void importQuestionsFromCSV(MultipartFile file) throws IOException {
        List<MultiQuestion> questions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            // Assuming the first line is the header
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 11) {
                    MultiQuestion question = new MultiQuestion();
                    question.setSubject(data[0]);
                    question.setQuestion(data[1]);
                    question.setAnswerA(data[2]);
                    question.setAnswerB(data[3]);
                    question.setAnswerC(data[4]);
                    question.setAnswerD(data[5]);
                    question.setRightAnswer(data[6]);
                    question.setAnalysis(data[7]);
                    try {
                        int score = Integer.parseInt(data[8]);
                        question.setScore(score);
                    } catch (NumberFormatException e) {
                        // 处理无效的分数，可以跳过该行或进行其他操作
                        // 例如，记录错误日志
                    }
                    question.setKnowledgeId(data[9]);
                    question.setDifficulty(data[10]);
                    questions.add(question);
                }
            }
        }
        saveQuestions(questions);
        }
    @Transactional
    public void saveQuestions(List<MultiQuestion> questions) {
        for (MultiQuestion question : questions) {
            // 在这里可以添加一些逻辑，比如验证题目是否有效等

            // 调用持久化层将题目保存到数据库
            multiQuestionRepository.insert(question);
        }
    }

    public String performOCR(String imagePath) {
        File imageFile = new File(imagePath);

        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("E://Tesseract-OCR//tessdata");
        tesseract.setLanguage("chi_sim");
        tesseract.setTessVariable("user_defined_dpi", "300");
        try {
            String result = tesseract.doOCR(imageFile);
            return result;
        } catch (TesseractException e) {
            e.printStackTrace();
            return "Error during OCR processing.";
        }
    }
    public List<MultiQuestion> selectByKnowledgeAndDifficulty(List<Integer> knowledgeIds, Integer difficulty) {
        LambdaQueryWrapper<MultiQuestion> wrapper = Wrappers.lambdaQuery();
        wrapper.in(MultiQuestion::getKnowledgeId, knowledgeIds)
                .eq(MultiQuestion::getDifficulty, difficulty);
        return multiQuestionRepository.selectList(wrapper);
    }

//    public MultiQuestion parseOCRResult(String ocrResult) {
//        MultiQuestion quiz = new MultiQuestion();
//
//        // 根据识别结果进行切割和提取信息
//        String[] parts = ocrResult.split("\\. "); // 按句号和空格分割
//
//        if (parts.length >= 3) {
//            quiz.setQuestion(parts[0]);
//
//            String optionsPart = parts[1];
//            String[] options = optionsPart.split("\\. "); // 按句号和空格分割
//
//            if (options.length >= 4) {
//                quiz.setAnswerA(options[0]);
//                quiz.setAnswerB(options[1]);
//                quiz.setAnswerC(options[2]);
//                quiz.setAnswerD(options[3]);
//            } else {
//                throw new IllegalArgumentException("Invalid options format");
//            }
//
//            String correctAnswerPart = parts[2];
//            quiz.setRightAnswer(correctAnswerPart.substring(correctAnswerPart.indexOf(": ") + 2));
//        } else {
//            System.out.println("Invalid OCR result format: " + ocrResult);
//            throw new IllegalArgumentException("Invalid OCR result format");
//        }
//
//        return quiz;
//    }


}
