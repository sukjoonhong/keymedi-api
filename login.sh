#!/bin/bash

# 현재 스크립트 위치 기준으로 공개키 경로 설정
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PUBLIC_KEY_PATH="$SCRIPT_DIR/application/src/main/resources/public_key.pem"

# 사용자 정보
USER_ID="user1"
PASSWORD="pass1"

# JSON 생성
JSON=$(jq -n \
  --arg userId "$USER_ID" \
  --arg password "$PASSWORD" \
  '{userId: $userId, password: $password}')

# 요청 전송
curl -X POST http://localhost:18080/v1/auth/login \
  -H "Content-Type: application/json" \
  -d "$JSON" \
  -i
