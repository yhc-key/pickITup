import Image from "next/image";
import Link from "next/link";

export default function LoginNeed() {

  return (
    <div className="max-w-[430px] min-h-[450px] mx-auto mt-24 px-5">

      <div className="flex flex-col justify-center items-center">
        <Image
          src="/images/speechBubble.png"
          alt="speechBubble"
          width={200}
          height={200}
          className="mb-10"
        ></Image>

        <div className="flex flex-col justify-around items-center">
          <div className="mb:text-2xl text-3xl font-semibold mb-10">
            해당 서비스는 <b className="text-f5green-300">회원 전용</b>입니다.
          </div>
          <div className="text-lg">이용을 위해 <Link href="/main/social" className="animate-[pulse_2s_ease-in_infinite] hover:font-semibold">로그인</Link>해주세요.</div>
        </div>
      </div>
    </div>
  );
}