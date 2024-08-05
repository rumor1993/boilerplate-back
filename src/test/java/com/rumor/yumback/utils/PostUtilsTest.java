package com.rumor.yumback.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostUtilsTest {

    @Test
    void extractDescription() {
        String contents = "<p>기다리던 파리올림픽이 곧 시작됩니다! 이번 올림픽에서 많은 사람들이 주목하고 있는 종목은 아무래도 수영이 아닐까 싶은데요. 수영 황금세대라 불리는 선수들의 경기를 보면서 메달 획득에 대한 기대는 물론, 이번 기회에 수영을 한 번 제대로 배워보고 싶다는 생각을 하는 분들...</p><p>dasda<p><img src='https://dcimg6.dcinside.co.kr/viewimage.php?id=2fb8d122f6d32ab2&amp;no=24b0d769e1d32ca73fe981fa11d028310a3eab44b7e01cf4f191b2ce2a89666c9bf34d9a44b28092becc115ec79a1066aa4c6d699375b7940a76dca346c0d6dfe40f28b4e00a24b89eae414cd8fcfe99bd356a01796d0c00fd8698c08b518d9b' alt='3fb8c32fffd711ab6fb8d38a4683746f7bcb93c7855f58c17af950032f76074c94ee42d5373f2bb21a608f8355'>";
        String description = PostUtils.extractDescription(contents, 150);

        Assertions.assertThat(description).isEqualTo("기다리던 파리올림픽이 곧 시작됩니다! 이번 올림픽에서 많은 사람들이 주목하고 있는 종목은 아무래도 수영이 아닐까 싶은데요. 수영 황금세대라 불리는 선수들의 경기를 보면서 메달 획득에 대한 기대는 물론, 이번 기회에 수영을 한 번 제대로 배워보고 싶다는 생각을 하는 분들");
    }
    @Test
    void extractImage() {
        String contents = "<p>기다리던 파리올림픽이 곧 시작됩니다! 이번 올림픽에서 많은 사람들이 주목하고 있는 종목은 아무래도 수영이 아닐까 싶은데요. 수영 황금세대라 불리는 선수들의 경기를 보면서 메달 획득에 대한 기대는 물론, 이번 기회에 수영을 한 번 제대로 배워보고 싶다는 생각을 하는 분들...</p><p>dasda<p><img src='https://dcimg6.dcinside.co.kr/viewimage.php?id=2fb8d122f6d32ab2&amp;no=24b0d769e1d32ca73fe981fa11d028310a3eab44b7e01cf4f191b2ce2a89666c9bf34d9a44b28092becc115ec79a1066aa4c6d699375b7940a76dca346c0d6dfe40f28b4e00a24b89eae414cd8fcfe99bd356a01796d0c00fd8698c08b518d9b' alt='3fb8c32fffd711ab6fb8d38a4683746f7bcb93c7855f58c17af950032f76074c94ee42d5373f2bb21a608f8355'> <img src='test'></img>";
        String image = PostUtils.extractImage(contents);

        Assertions.assertThat(image).isEqualTo("https://dcimg6.dcinside.co.kr/viewimage.php?id=2fb8d122f6d32ab2&no=24b0d769e1d32ca73fe981fa11d028310a3eab44b7e01cf4f191b2ce2a89666c9bf34d9a44b28092becc115ec79a1066aa4c6d699375b7940a76dca346c0d6dfe40f28b4e00a24b89eae414cd8fcfe99bd356a01796d0c00fd8698c08b518d9b");
    }
}