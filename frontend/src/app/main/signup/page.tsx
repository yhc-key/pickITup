"use client";
import { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
export default function Signup() {
  const router = useRouter();
  const [availableId, setAvailableId] = useState<string>("");
  const [id, setId] = useState<string>("");

  const [isValidPassword, setIsValidPassword] = useState<string>("");
  const [password, setPassword] = useState<string>("");

  const [samepass, setSamepass] = useState<string>("");
  const [issame, setIssame] = useState<boolean>(false);

  const [name, setName] = useState<string>("");
  const [email, setEmail] = useState<string>("");

  useEffect(() => {
    if (samepass === password) {
      setIssame(true);
    } else {
      setIssame(false);
    }
  }, [samepass, password]);

  useEffect(() => {
    // 비밀번호 유효성 검사
    const regex = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
    if (regex.test(password)) {
      setIsValidPassword("true");
    } else {
      setIsValidPassword("false");
    }
  }, [password]);
  ``;
  const checkid = () => {
    if (id === "") {
      setAvailableId("false");
      return;
    }
    fetch("https://spring.pickitup.online/auth/check/" + id, {
      method: "POST",
    })
      .then((res) => res.json())
      .then((res) => {
        if (res.success === true) {
          setAvailableId("true");
        } else if (res.success === false) {
          setAvailableId("false");
        }
      });
  };
  const signUpRequest = () => {
    if (availableId !== "true") {
      alert("중복확인 후 올바른 아이디를 다시 입력해주세요");
      return;
    }
    if (!isValidPassword) {
      alert("올바른 비밀번호를 다시 입력해주세요");
      return;
    }
    if (!issame) {
      alert("동일한 비밀번호를 다시 입력해주세요");
      return;
    }
    if (name === "") {
      alert("이름을 작성해주세요");
      return;
    }
    fetch("https://spring.pickitup.online/auth/signup", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username: id,
        password: password,
        name: name,
        nickname: name,
        email: email,
      }),
    })
      .then((res) => res.json())
      .then((res) => {
        console.log(res);
        router.push("/main/recruit");
      });
  };
  return (
    <div className="flex flex-col justify-center items-center w-full h-[82vh]">
      <div className="flex items-center justify-center h-[10vh] text-xl font-bold">
        pick IT up 회원가입
      </div>
      <div className="w-[48vw] h-[62vh] rounded-[10px] border border-f5gray-400">
        <div>
          <div className="flex w-full h-[6vh] justify-center items-center mt-4">
            <label htmlFor="id" className="w-[10vw] text-lg font-black">
              아이디
            </label>
            <input
              value={id}
              onChange={(e) => setId(e.target.value)}
              className="w-[16vw] h-[4vh] rounded-md  border border-f5gray-400
              bg-gray-200 appearance-none border-2 border-gray-200 
              text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-f5green-300 
              "
            />
            <div className="w-[1vw]"></div>
            <button
              onClick={(e) => {
                checkid(), e.preventDefault();
              }}
              className="w-[7vw] h-[4vh] flex items-center justify-center rounded-md bg-f5green-300 text-xs text-white font-bold"
            >
              중복확인
            </button>
          </div>
          <div className="flex w-full h-[1vh] justify-center items-center mb-2">
            <div className="w-[10vw]"></div>
            {availableId === "true" ? (
              <div className="w-[24vw] text-xs text-[#5A85C5]">
                사용 가능한 아이디 입니다!
              </div>
            ) : (
              <></>
            )}
            {availableId === "false" ? (
              <div className="w-[24vw] text-xs text-[#C55A5A]">
                사용할 수 없는 아이디입니다!
              </div>
            ) : (
              <></>
            )}
          </div>

          <div className="flex w-full h-[6vh] justify-center items-center">
            <label htmlFor="password" className="w-[10vw] text-lg font-black">
              비밀번호
            </label>
            <input
              type="password"
              value={password}
              onChange={(e) => {
                setPassword(e.target.value);
              }}
              className="w-[24vw] h-[4vh] rounded-md  border border-f5gray-400
              bg-gray-200 appearance-none border-2 border-gray-200
              text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-f5green-300 
              "
            />
          </div>
          <div className="flex w-full h-[1vh] justify-center items-center mb-2">
            <div className="w-[10vw]"></div>
            <div className="w-[24vw] text-xs text-[#C55A5A]">
              {isValidPassword === "false"
                ? "영문자, 숫자를 포함하여 8자 이상을 입력해주세요"
                : ""}
            </div>
          </div>

          <div className="flex w-full h-[6vh] justify-center items-center">
            <label htmlFor="password" className="w-[10vw] text-lg font-black">
              비밀번호 확인
            </label>
            <input
              type="password"
              value={samepass}
              onChange={(e) => setSamepass(e.target.value)}
              className="w-[24vw] h-[4vh] rounded-md  border border-f5gray-400
              bg-gray-200 appearance-none border-2 border-gray-200 
              text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-f5green-300 
              "
            />
          </div>
          <div className="flex w-full h-[1vh] justify-center items-center mb-2">
            <div className="w-[10vw]"></div>
            <div className="w-[24vw] text-xs text-[#C55A5A]">
              {issame ? "" : "위의 비밀번호와 동일하게 입력해주세요"}
            </div>
          </div>

          <div className="flex w-full h-[6vh] justify-center items-center">
            <div className="w-[10vw] text-lg font-black">이름</div>
            <input
              value={name}
              onChange={(e) => {
                setName(e.target.value);
              }}
              className="w-[24vw] h-[4vh] rounded-md  border border-f5gray-400
              bg-gray-200 appearance-none border-2 border-gray-200
              text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-f5green-300 
              "
            />
          </div>

          <div className="w-full h-[1vh] mb-2" />

          <div className="flex w-full h-[6vh] justify-center items-center">
            <label className="w-[10vw] text-lg font-black">이메일 인증</label>
            <input
              value={email}
              onChange={(e) => {
                setEmail(e.target.value);
              }}
              placeholder="example@naver.com"
              className="w-[16vw] h-[4vh] rounded-md  border border-f5gray-400
              bg-gray-200 appearance-none border-2 border-gray-200
              text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-f5green-300
              "
            />
            <div className="w-[1vw]"></div>
            <div className="w-[7vw] h-[4vh] flex items-center justify-center rounded-md bg-f5green-300 text-xs text-white font-bold">
              인증번호 요청
            </div>
            {/* <div className='w-[7vw] h-[4vh] flex items-center justify-center rounded-md bg-f5green-300 text-white font-bold'>재요청</div> */}
          </div>
          <div className="flex w-full h-[6vh] justify-center items-center">
            <div className="w-[10vw] font-black"></div>
            <input
              placeholder="인증번호 입력"
              className="w-[16vw] h-[4vh] rounded-md  border border-f5gray-400
              bg-gray-200 appearance-none border-2 border-gray-200
              text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-f5green-300 
              "
            />
            <div className="w-[1vw]"></div>
            <div className="w-[7vw] h-[4vh] flex items-center justify-center rounded-md bg-f5green-300 text-xs text-white font-bold">
              인증
            </div>
            {/* <div className='w-[7vw] h-[4vh] flex items-center justify-center rounded-md bg-f5green-300 text-white font-bold'>인증완료</div> */}
          </div>

          <div className="flex w-full h-[14vh] justify-center items-center">
            <button
              onClick={signUpRequest}
              className="w-[24vw] h-[6vh] rounded-md bg-f5green-300 text-white font-bold"
            >
              회원가입
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
