# intellij-encryption-decryption

![Build](https://github.com/jeongyong95/intellij-encryption-decryption/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/MARKETPLACE_ID.svg)](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/MARKETPLACE_ID.svg)](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID)

<!-- Plugin description -->
IntelliJ IDE에서 문자열 암호화/복호화, 해시 생성, Base64 인코딩/디코딩을 수행할 수 있는 Tool Window 플러그인입니다.

외부 도구 없이 IDE 내에서 바로 암호화 작업을 처리할 수 있습니다.

### 주요 기능

- **암호화/복호화** - AES (128/192/256, CBC/GCM), DES, 3DES 지원
- **해시 생성** - MD5, SHA-1, SHA-256, SHA-512 지원
- **Base64** - 인코딩/디코딩 지원
<!-- Plugin description end -->

## 상세 기능

### 암호화/복호화

| 알고리즘 | 키 크기 | 모드 |
|---------|--------|------|
| AES-128 CBC | 128bit | CBC/PKCS5Padding |
| AES-192 CBC | 192bit | CBC/PKCS5Padding |
| AES-256 CBC | 256bit | CBC/PKCS5Padding |
| AES-128 GCM | 128bit | GCM/NoPadding |
| AES-192 GCM | 192bit | GCM/NoPadding |
| AES-256 GCM | 256bit | GCM/NoPadding |
| DES CBC | 64bit | CBC/PKCS5Padding |
| 3DES CBC | 192bit | CBC/PKCS5Padding |

- 비밀번호 기반 키 파생: PBKDF2WithHmacSHA256 (65,536 iterations)
- 출력 포맷: `Base64(salt + iv + ciphertext)` (self-contained)
- 매번 랜덤 Salt/IV를 생성하여 동일한 입력이라도 다른 암호문 출력

### 해시 생성

MD5, SHA-1, SHA-256, SHA-512 알고리즘을 지원하며, 결과는 Hex 문자열로 출력됩니다.

### Base64

UTF-8 문자열의 Base64 인코딩/디코딩을 지원합니다.

## 사용 방법

1. IDE 우측 Tool Window 바에서 **Crypto** 탭을 클릭합니다.
2. 암호화/복호화, 해시, Base64 탭 중 원하는 기능을 선택합니다.
3. 입력값을 입력하고 버튼을 클릭하면 결과가 출력됩니다.
4. **복사** 버튼으로 결과를 클립보드에 복사할 수 있습니다.

## 설치

- IDE 내장 플러그인 시스템:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "intellij-encryption-decryption"</kbd> >
  <kbd>Install</kbd>

- 수동 설치:

  [최신 릴리스](https://github.com/jeongyong95/intellij-encryption-decryption/releases/latest)를 다운로드한 후
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

## 요구 사항

- IntelliJ IDEA 2025.2.5 이상
- JDK 21 이상

## 기술 스택

- Kotlin
- IntelliJ Platform SDK
- JDK 내장 `javax.crypto` (추가 의존성 없음)
- Swing + JetBrains UI 컴포넌트

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
