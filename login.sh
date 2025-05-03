#!/bin/bash

# 현재 스크립트 위치 기준으로 공개키 경로 설정
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PUBLIC_KEY_PATH="$SCRIPT_DIR/application/src/main/resources/public_key.pem"

AUTH_ID="user1"
PASSWORD="pass1"

# RSA 암호화 후 base64 인코딩
ENCRYPTED_PASSWORD=$(echo -n "$PASSWORD" | openssl rsautl -encrypt -pubin -inkey "$PUBLIC_KEY_PATH" | base64)

JSON=$(jq -n \
  --arg authId "$AUTH_ID" \
  --arg password "$ENCRYPTED_PASSWORD" \
  '{authId: $authId, password: $password}'
)

curl -X POST http://localhost:18080/v1/auth/login \
  -H "Content-Type: application/json" \
  -d "$JSON" \
  -i
