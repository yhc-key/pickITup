'use client';

import Link from "next/link";
import { usePathname } from "next/navigation";

interface LinkType {
  name: string;
  href: string;
}

const navLinks: LinkType[] = [
  { name: "채용공고", href: "/recruit" },
  { name: "기술블로그", href: "/techBlog" },
  { name: "미니 게임", href: "/game" },
  { name: "면접 대비", href: "/interview" },
];

export default function Header() {
  const pathname = usePathname();
  const isActive = (path :string) => path === pathname;

  return (
    <div className="flex text-center justify-center">
      {navLinks.map((link: LinkType) => {

        return (
          <Link
            href={link.href}
            key={link.name}
            className={isActive(link.href) ? "font-bold mr-4" : "text-blue-500 mr-4"}
          >
            {link.name}
          </Link>
        );
      })}
    </div>
  );
}
