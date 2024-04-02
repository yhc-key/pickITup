
"use client";
import DaumPostcode from "react-daum-postcode";
import Modal from "@/components/modal2";
import { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import Swal from "sweetalert2";
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
  const [address, setAddress] = useState<string>("");
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);

  const clickSide = () => {
    setIsModalOpen(false);
    setIsOpen(false);
  }
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

  const completeHandler = (data: any) => {
    setAddress(data.address);
    setIsOpen(false); //추가
    setIsModalOpen(false);
  }

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
      Swal.fire({
        title: 'ID Check Error!',
        text: "중복확인 후 올바른 아이디를 다시 입력해주세요",
        icon: 'warning',
        confirmButtonText: '확인',
        confirmButtonColor: '#00ce7c'
      })
      return;
    }
    if (isValidPassword!=="true") {
      Swal.fire({
        title: 'Password Error!',
        text: "영문, 숫자 포함 8자 이상 비밀번호를 입력해주세요",
        icon: 'warning',
        confirmButtonText: '확인',
        confirmButtonColor: '#00ce7c'
      })
      return;
    }
    if (!issame) {
      Swal.fire({
        title: 'Password Check Error!',
        text: "동일한 비밀번호를 다시 입력해주세요",
        icon: 'warning',
        confirmButtonText: '확인',
        confirmButtonColor: '#00ce7c'
      })
      return;
    }
    if (name === "") {
      Swal.fire({
        title: 'No Name Error!',
        text: "이름을 작성해주세요",
        icon: 'warning',
        confirmButtonText: '확인',
        confirmButtonColor: '#00ce7c'
      })
      return;
    }
    if (email === "") {
      Swal.fire({
        title: 'No Email Error!',
        text: "이메일을 작성해주세요",
        icon: 'warning',
        confirmButtonText: '확인',
        confirmButtonColor: '#00ce7c'
      })
      return;
    }
    if (address === "") {
      Swal.fire({
        title: 'No Address Error!',
        text: "주소를 검색 후 선택해주세요.",
        icon: 'warning',
        confirmButtonText: '확인',
        confirmButtonColor: '#00ce7c'
      })
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
        address: address,
      }),
    })
      .then((res) => res.json())
      .then((res) => {
        console.log(res);
        Swal.fire({
          title: 'Sign up Success!',
          text: "회원가입이 완료되었습니다!",
          icon: 'success',
          timer: 2000,
          confirmButtonText: '확인',
          confirmButtonColor: '#00ce7c'
        })
        router.push("/main/login");
      });
  };

  return (
    <div className="flex flex-col items-center justify-center w-full mt-10">
      <div className="min-w-[400px] mx-auto mb:min-w-[350px]">
        <div className="flex items-center justify-center mb-12 text-2xl font-bold text-f5black-400">
          회원가입
        </div>

        <div className="flex flex-col gap-4">{/* border border-f5gray-400*/}
          <div className="flex flex-col">
            <div className="flex items-center justify-center mt-4">
              <label htmlFor="id" className="w-32 ">
                아이디
              </label>
              <input
                value={id}
                onChange={(e) => { setId(e.target.value); setAvailableId("check"); }}
                placeholder="아이디를 입력해주세요."
                className="px-3 py-2 text-sm border rounded-md appearance-none w-52 min-h-10 border-f5gray-400 placeholder:text-f5gray-400 text-f5black-400 focus:outline-none focus:bg-white focus:border-f5green-300"
              />
              <button
                onClick={(e) => {
                  checkid(), e.preventDefault();
                }}
                
                className="flex items-center justify-center w-16 h-10 py-3 my-auto ml-4 text-xs text-white rounded-md bg-f5green-300"
              >
                중복확인
              </button>
            </div>
            <div className="min-h-5 flex items-center justify-start pt-1 text-xs pl-[8.5rem]">
              {availableId === "true" ? (
                <div className="text-[#5A85C5]">
                  사용 가능한 아이디 입니다!
                </div>
              ) : (
                <></>
              )}
              {availableId === "false" ? (
                <div className="text-[#C55A5A]">
                  사용할 수 없는 아이디입니다!
                </div>
              ) : (
                <></>
              )}
              {availableId === "check" ? (
                <div className="text-[#C55A5A]">
                  중복확인을 해주세요!
                </div>
              ) : (
                <></>
              )}
            </div>
          </div>

          <div className="flex flex-col">
            <div className="flex items-center justify-center">
              <label htmlFor="password" className="w-32 ">
                비밀번호
              </label>
              <input
                type="password"
                value={password}
                onChange={(e) => {
                  setPassword(e.target.value);
                }}
                placeholder="비밀번호를 입력해주세요."
                className="px-3 py-2 text-sm border rounded-md appearance-none w-72 min-h-10 border-f5gray-400 placeholder:text-f5gray-400 text-f5black-400 focus:outline-none focus:bg-white focus:border-f5green-300"
              />
            </div>
            <div className="min-h-5 flex items-center justify-start pt-1 text-xs pl-[8.5rem]">
              <div className="text-[#C55A5A]">
                {isValidPassword === "false"
                  ? "영문자, 숫자를 포함하여 8자 이상을 입력해주세요"
                  : ""}
              </div>
            </div>
          </div>

          <div className="flex flex-col">
            <div className="flex items-center justify-center h-10">
              <label htmlFor="password" className="w-32 ">
                비밀번호 확인
              </label>
              <input
                type="password"
                value={samepass}
                onChange={(e) => setSamepass(e.target.value)}
                placeholder="비밀번호를 다시 한 번 입력해주세요."
                className="px-3 py-2 text-sm border rounded-md appearance-none w-72 min-h-10 border-f5gray-400 placeholder:text-f5gray-400 text-f5black-400 focus:outline-none focus:bg-white focus:border-f5green-300"
              />
            </div>
            <div className="min-h-5 flex items-center justify-start pt-1 text-xs pl-[8.5rem]">
              <div className=" text-[#C55A5A] ">
                {issame ? "" : "비밀번호가 서로 일치하지 않습니다."}
              </div>
            </div>
          </div>

          <div className="flex items-center justify-center mb-5">
            <div className="w-32 ">이름</div>
            <input
              value={name}
              onChange={(e) => {
                setName(e.target.value);
              }}
              placeholder="이름을 입력해주세요."
              className="px-3 py-2 text-sm border rounded-md appearance-none w-72 min-h-10 border-f5gray-400 placeholder:text-f5gray-400 text-f5black-400 focus:outline-none focus:bg-white focus:border-f5green-300"
            />
          </div>
          <div className="flex items-center justify-center mb-5">
            <label className="w-32">이메일</label>
            <input
              value={email}
              onChange={(e) => {
                setEmail(e.target.value);
              }}
              placeholder="이메일을 입력해주세요."
              className="px-3 py-2 text-sm border rounded-md appearance-none w-72 min-h-10 border-f5gray-400 placeholder:text-f5gray-400 text-f5black-400 focus:outline-none focus:bg-white focus:border-f5green-300"
            />
          </div>
          <div className="flex items-center justify-center">
            <label className="w-32 ">
              주소
            </label>
            <input
              value={address}
              onChange={(e) => { setAddress(e.target.value) }}
              placeholder="주소를 검색해주세요."
              disabled
              className="px-3 py-2 text-sm border rounded-md appearance-none min-h-10 text-f5black-400 w-60 border-f5gray-400 readOnly"
            />
            <button
              onClick={(e) => { setIsModalOpen(true), setIsOpen(true); }}
              className="flex items-center justify-center w-12 h-10 ml-1 text-xs text-white rounded-md bg-f5green-300 "
            >찾기</button>
            {isOpen && <Modal open={isModalOpen} clickSide={clickSide} size="h-4/6 w-1/2" >
              <DaumPostcode onComplete={completeHandler} />
            </Modal>}
          </div>
          <div className="flex items-center justify-center w-full mt-10">
            <button
              onClick={signUpRequest}
              className="w-full h-12 font-semibold text-white rounded-md bg-f5green-300">
              가입하기
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
