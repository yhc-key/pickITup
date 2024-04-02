import Image from "next/image";
import Link from "next/link";

export default function ChooseFirst() {

  return (
    <div className="max-w-[600px] min-h-[450px] mx-auto mt-24 px-5">

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
            해당 서비스는 현재 <b className="text-f5red-300">제한 서비스</b>입니다.
          </div>
          <div className="text-lg">이용을 위해 <Link href="/main/recruit" className="text-f5green-300 animate-puls font-semibold hover:font-bold">기술 스택을 선택</Link>해주세요.</div>
        </div>
      </div>
    </div>
  );
}