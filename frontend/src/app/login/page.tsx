"use client"
import { useRouter } from 'next/navigation';
import Image from 'next/image'
import Link from 'next/link'
import { useState } from 'react';
function Login(){
  const router = useRouter();
  const [id,setId] = useState("");
  const [password,setPassword] = useState(""); 
  const requestLogin = () =>{    
    if(id.length === 0){
      alert("아이디를 입력해주세요!")
      return;
    }
    if(password.length === 0){
      alert("비밀번호를 입력해주세요!")
      return;
    }
    fetch("https://spring.pickitup.online/auth/login",{
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        username : id,
        password : password
      })
    })
    .then(res=>res.json())
    .then(res => {
      sessionStorage.setItem('accessToken', res.response.accessToken);
      sessionStorage.setItem('refreshToken', res.response.refreshToken);
      alert(sessionStorage.getItem('accessToken'))
      router.push('/myPage/myBadge');
    })
    .catch(e=>{
      alert("아이디 혹은 비밀번호가 일치하지 않습니다.");
      return;
    })
  }
  return (
    <div className="flex flex-col justify-center items-center w-full h-[70vh]">

      <div className="flex items-center justify-center h-[10vh] text-xl font-bold">pick IT up 로그인</div>
      <div className="h-[30vh] w-[45vw] h-[34vh] rounded-[10px] border border-[#d9d9d9]">
        
        <form>
          <div className='flex w-full h-[6vh] justify-center items-center mt-14'>
            <label htmlFor="id" className="w-[6vw] font-black">아이디</label>
            <input value={id} onChange={(e)=>setId(e.target.value)} 
            placeholder="아이디를 입력하세요" type="text" required
            className="w-[20vw] ml-6 rounded-md bg-[#f5f5f5] border border-[#d9d9d9]"/>
          </div>

          <div className='flex w-full h-[6vh] justify-center items-center'>
            <label htmlFor="password" className='w-[6vw] font-black'>비밀번호</label>
            <input value={password} onChange={(e)=>setPassword(e.target.value)} 
            placeholder="비밀번호를 입력하세요" type="password" required
            className="w-[20vw] ml-6 rounded-md bg-[#f5f5f5] border border-[#d9d9d9]"/>
          </div>

          <div className="flex w-full h-[14vh] justify-center items-center">
            <button type="submit" onClick={(e)=>{e.preventDefault(), requestLogin()}} 
            className="w-[18vw] h-[5vh] rounded-md bg-[#00ce7c] text-white text-lg font-bold">로그인</button>
          </div>
        </form>

      </div>
      <div className="flex items-center justify-center h-[8vh] whitespace-pre">아직 계정이 없으신가요? <Link href="/signup" className="text-lg font-bold">회원가입 하러가기</Link></div>
    </div>
  )
}
export default Login;