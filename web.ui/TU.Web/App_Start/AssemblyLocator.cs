using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Web;
using System.Web.Compilation;

namespace TU.Web
{
    internal static class AssemblyLocator
    {
        public static IEnumerable<Assembly> GetAssemblies(string containingName = null, bool includeBinFolderAssemblies = false)
        {
            var assemblies = BuildManager.GetReferencedAssemblies().Cast<Assembly>().ToList();

            if (!string.IsNullOrWhiteSpace(containingName))
            {
                assemblies = assemblies.Where(a => a.FullName.StartsWith(containingName)).ToList();
            }

            if (includeBinFolderAssemblies)
            {
                var binFolderAssemblies = GetBinFolderAssemblies(containingName)
                    .Select(a => a.GetName())
                    .SelectMany(name => assemblies.SkipWhile(referencedAssembly => AssemblyName.ReferenceMatchesDefinition(referencedAssembly.GetName(), name)))
                    .ToList();

                assemblies.AddRange(binFolderAssemblies);
            }

            return assemblies;
        }

        private static IEnumerable<Assembly> GetBinFolderAssemblies(string containingName = null)
        {
            IList<Assembly> binFolderAssemblies = new List<Assembly>();

            string binFolder = HttpRuntime.BinDirectory;
            List<string> dllFiles = Directory.GetFiles(binFolder, "*.dll", SearchOption.TopDirectoryOnly).ToList();

            if (!string.IsNullOrWhiteSpace(containingName))
            {
                dllFiles = dllFiles.Where(fn => fn.Contains(containingName)).ToList();
            }

            dllFiles.ForEach(f => binFolderAssemblies.Add(Assembly.LoadFile(f)));

            return binFolderAssemblies;
        }
    }
}