package jp.ac.hcs.GreenShower;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;

/**
 * WebConfigクラス
 */
@Configuration
public class WebConfig {

	/** 出力パス */
	public static final String OUTPUT_PATH = "target/";

	/** タスク情報のCSVファイル名 */
	public static final String FILENAME_REPORT_CSV = "report.csv";
	
	/** 就職申請一覧情報のCSVファイル名 */
	public static final String FILENAME_JOBREQUEST_CSV = "request.csv";
	/**
	 * メッセージのパラメーター化と国際化をサポートする、メッセージを解決するための戦略インターフェース。
	 * @return bean
	 */
	@Bean
	public MessageSource messageSource() {

		//メッセージプロパティのファイル設定
		ReloadableResourceBundleMessageSource bean = new ReloadableResourceBundleMessageSource();
		bean.setBasename("classpath:messages");
		bean.setDefaultEncoding("UTF-8");

		return bean;
	}

	/**
	 * Spring アプリケーションコンテキストでの javax.validation （JSR-303）セットアップの中心的なクラスです
	 * @return localValidatorFactoryBean
	 */
	@Bean
	public LocalValidatorFactoryBean localValidatorFactoryBean() {

		// バリデーションのメッセージ設定
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.setValidationMessageSource(messageSource());

		return localValidatorFactoryBean;
	}
	
	/**
	 * HTTP リクエストを実行する
	 * 
	 * @return RestTemplate
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
