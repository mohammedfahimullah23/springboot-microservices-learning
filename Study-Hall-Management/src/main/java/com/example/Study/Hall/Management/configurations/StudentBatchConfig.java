//package com.example.Study.Hall.Management.configurations;
//
//import com.example.Study.Hall.Management.model.Student;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.repository.support.MongoJobRepositoryFactoryBean;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.data.MongoItemWriter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.MongoDatabaseFactory;
//import org.springframework.data.mongodb.MongoTransactionManager;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.time.LocalDate;
//
//@Configuration
//@EnableBatchProcessing
//public class StudentBatchConfig {
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @Bean
//    public MongoTransactionManager transactionManager(MongoTemplate mongoTemplate) {
//        MongoDatabaseFactory mongoDatabaseFactory = mongoTemplate.getMongoDatabaseFactory();
//        return new MongoTransactionManager(mongoDatabaseFactory);  // Provides transaction management for MongoDB
//    }
//
//    @Bean
//    public JobRepository jobRepository(MongoTemplate mongoTemplate, MongoTransactionManager transactionManager) throws Exception {
//        MongoJobRepositoryFactoryBean jobRepositoryFactoryBean = new MongoJobRepositoryFactoryBean();
//        jobRepositoryFactoryBean.setMongoOperations(mongoTemplate);
//        jobRepositoryFactoryBean.setTransactionManager(transactionManager);
//        jobRepositoryFactoryBean.afterPropertiesSet();
//        return jobRepositoryFactoryBean.getObject();
//    }
//
//    @Bean
//    public Job importStudentJob(JobRepository jobRepository, MongoTransactionManager transactionManager) {
//        return new JobBuilder("importStudentJob", jobRepository)
//                .incrementer(new RunIdIncrementer()) // Optionally add a RunIdIncrementer for unique job instances
//                .start(step1(jobRepository, transactionManager)) // Pass transaction manager to step1
//                .build();
//    }
//    @Bean
//    public Step step1(JobRepository jobRepository, MongoTransactionManager transactionManager) {
//        return new StepBuilder("step1", jobRepository)
//                .<Student, Student>chunk(10, transactionManager) // Updated to include transaction manager
//                .reader(excelReader())
//                .processor(processor())
//                .writer(writer(mongoTemplate))
//                .build();
//    }
//
//
//    @Bean
//    public ItemReader<Student> excelReader() {
//        return new ItemReader<Student>() {
//            private XSSFWorkbook workbook;
//            private int currentRow = 0;
//
//            @Override
//            public Student read() throws Exception {
//                if (workbook == null) {
//                    workbook = new XSSFWorkbook(new FileInputStream("C://Users//moham//Desktop//students.xlsx"));
//                }
//
//                Row row = workbook.getSheetAt(0).getRow(currentRow++);
//                if (row == null) {
//                    workbook.close();
//                    return null; // End of file
//                }
//
//                Student student = new Student();
//                student.setName(row.getCell(0).getStringCellValue());
//                student.setBloodGroup(row.getCell(1).getStringCellValue());
//                student.setDateOfBirth(LocalDate.from(row.getCell(2).getLocalDateTimeCellValue()));
//                student.setPhoneNumber(row.getCell(3).getStringCellValue());
//                student.setExamPreparation(row.getCell(4).getStringCellValue());
//
//                return student; // Return the populated Student object.
//            }
//        };
//    }
//
//    @Bean
//    public ItemProcessor<Student, Student> processor() {
//        return student -> {
//            return student; // Return processed student object.
//        };
//    }
//
////    @Bean
////    public ItemWriter<Student> writer() {
////        MongoItemWriter<Student> writer = new MongoItemWriter<>();
////        writer.setTemplate(mongoTemplate);
////        writer.setCollection("students");
////        return writer;
////    }
//
//    @Bean
//    public ItemWriter<Student> writer(MongoTemplate mongoTemplate) {
//        return items -> {
//            for (Student student : items) {
//                mongoTemplate.save(student, "students");  // Save each student to the "students" collection
//            }
//        };
//    }
//}