package jp.ac.hcs.GreenShower.job.common;

import java.util.Arrays;

/**
 * コード定義Enum専用のインターフェース
 * 
 * @param E extends Enum<E> EnumまたはEnumを継承したクラス
 */
public interface CommonEnum <E extends Enum<E>>{
	
	/** ID返却 */
	String getId();

	/** 値返却 */
	String getValue();
	
	/**
	 * 表示の順番を返却する
	 * @return 表示の順番
	 */
    default int getOrder() {
        return Integer.parseInt(getValue());
    }
 
    /**
     * Enumに変換するデフォルトメソッド
     * @return Enumに変換されたエレメント
     */
    @SuppressWarnings("unchecked")
    default E toEnum() {
        return (E) this;
    }
    
    
    /**
     * IDが同値かチェックするデフォルトメソッド
     * @param id EnumのID
     * @return true(等しい) or false(等しくない)
     */
    default boolean equalsById(String id) {
        return getId().equals(id);
    }
	
    /**
     * 指定されたCommonEnumを実装したEnumの、指定されたIDの列挙子を返却する
     * @param <E>CommonEnumを継承したEnum
     * @param clazz CommonEnumのクラス
     * @param id 
     * @return -成功時：IDに合致したEnum -失敗時：null
     */
    static <E extends Enum<E>> E getEnum(Class<? extends CommonEnum<E>> clazz, String id) {
        return Arrays.stream(clazz.getEnumConstants())
                .filter(e -> e.equalsById(id))
                .map(CommonEnum::toEnum)
                .findFirst()
                .orElse(null);
    }
}
